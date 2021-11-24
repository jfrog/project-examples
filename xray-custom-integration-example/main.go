package main

import (
	"encoding/json"
	"fmt"
	"github.com/Masterminds/semver"
	"github.com/gorilla/handlers"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"strings"
	"time"
)

const providerName = "custom-integration-demo"
const InvalidAPIKeyMessage = "Invalid Api Key"
const port = ":8080"

type CheckAuthResponse struct {
	Valid bool
	Error string
}

type Component struct {
	ComponentID string `json:"component_id"`
	Blobs       []string
}

// Component data provided by XRay. We can use this to look up components.
type ComponentInfoRequest struct {
	Components []Component
	Context    string
}

type Vulnerability struct {
	CVE         string
	Version     string
	Type        string
	SourceID    string `json:"source_id"`
	Summary     string
	Description string
	CVSSV2      string `json:"cvss_v2"`
	URL         string
	PublishDate string `json:"publish_date"`
	References  []string
}

type License struct {
	Version  string
	Licenses []string
}

// The shape of a component in the db used by this demo
type ComponentRecord struct {
	ComponentID     string `json:"component_id"`
	Licenses        []License
	Vulnerabilities []Vulnerability
}

type ComponentInfo struct {
	ComponentID     string `json:"component_id"`
	Licenses        []string
	Provider        string // This should always be the name of your provider
	Vulnerabilities []Vulnerability
}

// The shape of the response returned by component info endpoint
// XRay uses this info to check for violations
type ComponentInfoResponse struct {
	Components []ComponentInfo
}

func main() {
	// The api key must be provided to XRay when configuring the integration
	// Only one api key is supported here, passed in as a CLI argument
	dbPath, apiKey, err := parseArgs()
	if err != nil {
		log.Fatalf("Something went wrong: %v\n", err)
	}
	// Create the routes required for any XRay integration.
	router := CreateRouter(dbPath, apiKey)
	log.Print("Server is starting on port ", port, "\n")
	// Instantiate the server with the router.
	s := &http.Server{
		Addr:           port,
		Handler:        handlers.LoggingHandler(log.Writer(), router),
		ReadTimeout:    10 * time.Second,
		WriteTimeout:   10 * time.Second,
		MaxHeaderBytes: 1 << 20,
	}
	// Run the server
	log.Fatal(s.ListenAndServe())
}

// Creates the routes required for any XRay integration.
func CreateRouter(dbPath string, apiKey string) *http.ServeMux {
	router := http.NewServeMux()
	// Routes: must be supplied to x-ray during integration setup as TestURL and URL
	router.HandleFunc("/api/checkauth", func(w http.ResponseWriter, r *http.Request) {
		checkAuth(w, r, apiKey)
	}) // TestURL
	router.HandleFunc("/api/componentinfo", func(w http.ResponseWriter, r *http.Request) {
		componentInfo(w, r, dbPath, apiKey)
	}) // URL
	return router
}

// Reads and validates command line arguments
func parseArgs() (string, string, error) {
	if len(os.Args) < 2 {
		return "", "", fmt.Errorf("Api key is required\nUsage: go run main.go (api-key) [path-to-db-file]")
	}
	apiKey := os.Args[1]
	dbPath := "db.json"
	if len(os.Args) > 2 {
		dbPath = os.Args[2]
	}
	return dbPath, apiKey, nil
}

// The Test URL you can use to test your API key with the provider using the "Test" button
func checkAuth(w http.ResponseWriter, r *http.Request, apiKey string) {
	// Our client (XRay) expects a response with json for both correct and incorrect api keys.
	resp := CheckAuthResponse{true, ""}
	key := r.Header.Get("apiKey")
	if key != apiKey {
		// If the key in the integration doesn't match the one we passed in through
		// the command line, change the json response to the expected FAILURE response.
		// XRay uses the json body of the response to check whether the key is valid, NOT status code.
		resp = CheckAuthResponse{Valid: false, Error: InvalidAPIKeyMessage}
	}
	js, err := json.Marshal(resp)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	// Write a 200 response, even when the client is not authorized.
	w.Write(js)
}

/**
This endpoint provides information to XRay about components

It implements the primary feature of this integration: reporting
information about vulnerabilities and licenses in software components
*/
func componentInfo(w http.ResponseWriter, r *http.Request, dbPath string, apiKey string) {
	// We need to check for authorization here, too.
	key := r.Header.Get("apiKey")
	if key != apiKey {
		// Unlike the "/checkauth" endpoint, "/componentinfo" can use a traditional 403
		// response when the client is not authorized.
		http.Error(w, InvalidAPIKeyMessage, http.StatusUnauthorized)
		return
	}
	body, err := ioutil.ReadAll(r.Body)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	requestPayload := ComponentInfoRequest{}
	err = json.Unmarshal(body, &requestPayload)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	// Get all the components from the "db".
	// The db is just a json file with fake data about components.
	db, err := getDB(dbPath)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	// Get matching components from db
	// If any of the components in the client's request match vulnerabilities
	// or licenses stored in the database, we will respond with data about those components.
	responsePayload, err := findComponents(requestPayload.Components, db)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	js, err := json.Marshal(responsePayload)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	w.Header().Set("Content-Type", "application/json")
	w.Write(js)
}

// Unmarshall data from the json db
func getDB(dbPath string) ([]ComponentRecord, error) {
	file, err := ioutil.ReadFile(dbPath)
	if err != nil {
		return nil, err
	}
	var data []ComponentRecord
	_ = json.Unmarshal(file, &data)
	return data, nil
}

// Search db for matching components and return
func findComponents(components []Component, db []ComponentRecord) (ComponentInfoResponse, error) {
	matches := ComponentInfoResponse{}
	// Check database for licenses and vulnerabilities matching the versions
	for _, component := range components {
		result := ComponentInfo{}
		name, version := getVersionAndNameFromComponentID(component.ComponentID)
		for _, item := range db {
			if item.ComponentID == name {
				// Any Matching Licenses?
				// Even if there are no vulnerabilities for the component,
				// we want to inform XRay about any licenses associated with this version.
				licenses, err := getLicensesForVersion(version, item.Licenses)
				if err != nil {
					return matches, err
				}
				// Any Matching Vulnerabilities?
				vulnerabilities, err := getVulnerabilitiesForVersion(version, item.Vulnerabilities)
				if err != nil {
					return matches, err
				}
				// If either a vulnerability or a license exists for the component,
				// add it to the list of components used in the componentInfo response
				if len(licenses) > 0 || len(vulnerabilities) > 0 {
					result = ComponentInfo{
						// Use the component_id provided by the client, NOT the one from our database.
						// The db's notion of a component_id does not include the component's version.
						// XRay needs us to include the version in this field.
						ComponentID: component.ComponentID,
						Licenses:    licenses,
						// Hardcode the provider name
						// We want this to match the one configured in the XRay integration
						Provider:        providerName,
						Vulnerabilities: vulnerabilities,
					}
					matches.Components = append(matches.Components, result)
					// There should be only one item in the db with matching results for a given component version,
					// so we can break the loop and stop searching.
					break
				}
			}
		}
	}
	return matches, nil
}

// Extract the version from the after the last ":" in the component ID
func getVersionAndNameFromComponentID(componentID string) (string, string) {
	index := strings.LastIndex(componentID, ":")
	name := ""
	version := ""
	if index > -1 {
		split := strings.SplitAfterN(componentID, ":", index)
		name = componentID[0:index]
		version = split[len(split)-1]
	}
	return name, version
}

// Get all the licenses for a given version
// The db associates each license with a version (and not merely a component) in the same struct,
// but XRay expects to receive license information in the form of a []string.
func getLicensesForVersion(version string, licenses []License) ([]string, error) {
	var matchingLicences []string
	for _, license := range licenses {
		isMatching, err := isVersionMatching(version, license.Version)
		if err != nil {
			return matchingLicences, err
		}
		if isMatching {
			// Only pick the licenses for the specified version of the component
			matchingLicences = append(matchingLicences, license.Licenses...)
		}
	}
	return matchingLicences, nil
}

// Get all the vulnerabilities for a given version
// The db associates each vulnerability with a version (and not merely a component) in the same struct
func getVulnerabilitiesForVersion(version string, vulnerabilities []Vulnerability) ([]Vulnerability, error) {
	var matchingVulnerabilities []Vulnerability
	for _, vulnerability := range vulnerabilities {
		isMatching, err := isVersionMatching(version, vulnerability.Version)
		if err != nil {
			return matchingVulnerabilities, err
		}
		if isMatching {
			// Only pick the vulnerabilities for the specified version of the component
			matchingVulnerabilities = append(matchingVulnerabilities, vulnerability)
		}
	}
	return matchingVulnerabilities, nil
}

// Only semver is supported
func isVersionMatching(componentVersion string, versionRange string) (bool, error) {
	// Use this semver library to check whether a version
	// is within a given range.
	constraint, err := semver.NewConstraint(versionRange)
	if err != nil {
		log.Println(err)
		return false, err
	}
	candidate, err := semver.NewVersion(componentVersion)
	if err != nil {
		log.Println(err)
		return false, err
	}
	return constraint.Check(candidate), nil
}
