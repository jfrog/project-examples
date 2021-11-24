package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
	"net/http/httptest"
	"testing"
)

const apiKey = "secretKey"

func TestApi(t *testing.T) {
	// Create test server
	ts := httptest.NewServer(CreateRouter("db.json", apiKey))
	defer ts.Close()

	// ComponentInfo endpoint
	t.Run("ComponentInfo: Valid Api Key", func(t *testing.T) {
		validAPIKeyTestComponentInfo(t, ts)
	})
	t.Run("ComponentInfo: Invalid Api Key", func(t *testing.T) {
		invalidAPIKeyTestComponentInfo(t, ts)
	})
	t.Run("Component with vulnerabilities", func(t *testing.T) {
		vulnerableComponentTest(t, ts)
	})
	t.Run("Component without vulnerabilities", func(t *testing.T) {
		healthyComponentTest(t, ts)
	})
	t.Run("Component not in database", func(t *testing.T) {
		notFoundComponentTest(t, ts)
	})
	t.Run("Version not in database", func(t *testing.T) {
		notFoundVersionTest(t, ts)
	})
	t.Run("Version without vulnerabilities", func(t *testing.T) {
		healthyVersionTest(t, ts)
	})
	t.Run("Multiple vulnerable components", func(t *testing.T) {
		vulnerableComponentsTest(t, ts)
	})

	// CheckAuth endpoint
	t.Run("CheckAuth: Valid Api Key", func(t *testing.T) {
		validAPIKeyTest(t, ts)
	})
	t.Run("CheckAuth: Invalid Api Key", func(t *testing.T) {
		invalidAPIKeyTest(t, ts)
	})
}

// Valid api keys
// should be accepted by the api
func validAPIKeyTestComponentInfo(t *testing.T, ts *httptest.Server) {
	req, err := http.NewRequest("GET", ts.URL+"/api/componentinfo", nil)
	if err != nil {
		t.Fatal(err)
	}
	req.Header.Set("apiKey", apiKey)
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		t.Fatal(err)
	}
	if resp.StatusCode == http.StatusUnauthorized {
		t.Error("Failed to validate api key: ", apiKey)
	}
}

// Invalid api keys
// should be rejected by the api with a 401 response
func invalidAPIKeyTestComponentInfo(t *testing.T, ts *httptest.Server) {
	req, err := http.NewRequest("GET", ts.URL+"/api/componentinfo", nil)
	invalidKey := "invalidAPIKey"
	if err != nil {
		t.Fatal(err)
	}
	req.Header.Set("apiKey", invalidKey)
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		t.Fatal(err)
	}
	if resp.StatusCode != http.StatusUnauthorized {
		t.Error("Invalid API key was accepted: ", invalidKey)
	}
}

// Querying a component with matching vulnerabilities in the database
// should result in a response containing data about the vulnerabilities
func vulnerableComponentTest(t *testing.T, ts *httptest.Server) {
	component := ComponentInfoRequest{
		Components: []Component{{
			ComponentID: "pypi://requests:2.22.0",
		}},
		Context: "foo",
	}
	data, err := json.Marshal(component)
	if err != nil {
		t.Fatal(err)
	}
	req, err := http.NewRequest("GET", ts.URL+"/api/componentinfo", bytes.NewBuffer(data))
	if err != nil {
		t.Fatal(err)
	}
	req.Header.Set("apiKey", apiKey)
	req.Header.Set("Content-Type", "application/json")
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		t.Fatal(err)
	}
	if resp.StatusCode != http.StatusOK {
		t.Error("Expected 200 status, received: ", resp.StatusCode)
	}
	defer resp.Body.Close()
	componentInfo := ComponentInfoResponse{}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		t.Fatal(err)
	}
	err = json.Unmarshal(body, &componentInfo)
	if err != nil {
		t.Fatal(err)
	}
	if len(componentInfo.Components) < 1 {
		t.Error("Unable to find component with id: ", component.Components[0].ComponentID, " in db file.")
	}
	if len(componentInfo.Components[0].Vulnerabilities) < 1 {
		t.Error("Expected component with id: ", component.Components[0].ComponentID, " to have a vulnerability, but it has none.")
	}
}

// Querying a component in the database without vulnerabilities
// should result in a response with empty vulnerability data
func healthyComponentTest(t *testing.T, ts *httptest.Server) {
	component := ComponentInfoRequest{
		Components: []Component{{
			ComponentID: "healthy://component:1.0.0",
		}},
		Context: "foo",
	}
	data, err := json.Marshal(component)
	if err != nil {
		t.Fatal(err)
	}
	req, err := http.NewRequest("GET", ts.URL+"/api/componentinfo", bytes.NewBuffer(data))
	if err != nil {
		t.Fatal(err)
	}
	req.Header.Set("apiKey", apiKey)
	req.Header.Set("Content-Type", "application/json")
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		t.Fatal(err)
	}
	if resp.StatusCode != http.StatusOK {
		t.Error("Expected 200 status, received: ", resp.StatusCode)
	}
	defer resp.Body.Close()
	componentInfo := ComponentInfoResponse{}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		t.Fatal(err)
	}
	err = json.Unmarshal(body, &componentInfo)
	if err != nil {
		t.Fatal(err)
	}
	if len(componentInfo.Components) < 1 {
		t.Error("Unable to find component with id: ", component.Components[0].ComponentID, " in db file.")
		return
	}
	if len(componentInfo.Components[0].Vulnerabilities) != 0 {
		t.Error("Expected component with id: ", component.Components[0].ComponentID, " to have no vulnerabilities, but one or more were found.")
	}
}

// Querying a component not in the database
// should result in a 200 status code response with empty component data
func notFoundComponentTest(t *testing.T, ts *httptest.Server) {
	component := ComponentInfoRequest{
		Components: []Component{{
			ComponentID: "notFoundComponent",
		}},
		Context: "foo",
	}
	data, err := json.Marshal(component)
	if err != nil {
		t.Fatal(err)
	}
	req, err := http.NewRequest("GET", ts.URL+"/api/componentinfo", bytes.NewBuffer(data))
	if err != nil {
		t.Fatal(err)
	}
	req.Header.Set("apiKey", apiKey)
	req.Header.Set("Content-Type", "application/json")
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		t.Fatal(err)
	}
	if resp.StatusCode != http.StatusOK {
		t.Error("Expected 200 status, received: ", resp.StatusCode)
	}
	defer resp.Body.Close()
	componentInfo := ComponentInfoResponse{}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		t.Fatal(err)
	}
	err = json.Unmarshal(body, &componentInfo)
	if err != nil {
		t.Fatal(err)
	}
	fmt.Println(componentInfo.Components)
	if len(componentInfo.Components) > 0 {
		t.Error("Expected no components to be found, but found", len(componentInfo.Components), " components.")
	}
}

// Querying a component that has non-matching versions in the database
// should result in a 200 status code response with empty component data
func notFoundVersionTest(t *testing.T, ts *httptest.Server) {
	component := ComponentInfoRequest{
		Components: []Component{{
			ComponentID: "healthy://component:2.0.0",
		}},
		Context: "foo",
	}
	data, err := json.Marshal(component)
	if err != nil {
		t.Fatal(err)
	}
	req, err := http.NewRequest("GET", ts.URL+"/api/componentinfo", bytes.NewBuffer(data))
	if err != nil {
		t.Fatal(err)
	}
	req.Header.Set("apiKey", apiKey)
	req.Header.Set("Content-Type", "application/json")
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		t.Fatal(err)
	}
	if resp.StatusCode != http.StatusOK {
		t.Error("Expected 200 status, received: ", resp.StatusCode)
	}
	defer resp.Body.Close()
	componentInfo := ComponentInfoResponse{}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		t.Fatal(err)
	}
	err = json.Unmarshal(body, &componentInfo)
	if err != nil {
		t.Fatal(err)
	}
	fmt.Println(componentInfo.Components)
	if len(componentInfo.Components) > 0 {
		t.Error("Expected no components to be found, but found", len(componentInfo.Components), " components.")
	}
}

// Querying a healthy version of a component that has a different version with vulnerabilities
// should result in a response with empty vulnerability data
func healthyVersionTest(t *testing.T, ts *httptest.Server) {
	component := ComponentInfoRequest{
		Components: []Component{{ComponentID: "pypi://requests:3.0.0"}},
		Context:    "foo",
	}
	data, err := json.Marshal(component)
	if err != nil {
		t.Fatal(err)
	}
	req, err := http.NewRequest("GET", ts.URL+"/api/componentinfo", bytes.NewBuffer(data))
	if err != nil {
		t.Fatal(err)
	}
	req.Header.Set("apiKey", apiKey)
	req.Header.Set("Content-Type", "application/json")
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		t.Fatal(err)
	}
	if resp.StatusCode != http.StatusOK {
		t.Error("Expected 200 status, received: ", resp.StatusCode)
	}
	defer resp.Body.Close()
	componentInfo := ComponentInfoResponse{}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		t.Fatal(err)
	}
	err = json.Unmarshal(body, &componentInfo)
	if err != nil {
		t.Fatal(err)
	}
	if len(componentInfo.Components) < 1 {
		t.Error("Unable to find component with id: ", component.Components[0].ComponentID, " in db file.")
		return
	}
	if len(componentInfo.Components[0].Vulnerabilities) != 0 {
		t.Error("Expected component with id: ", component.Components[0].ComponentID, " to have no vulnerabilities, but one or more were found.")
	}
	if len(componentInfo.Components[0].Licenses) == 0 {
		t.Error("Expected component with id: ", component.Components[0].ComponentID, " to have a license, but none was found.")
	}

}

// Querying multiple vulnerable components
// should result in a response with vulnerability data about each component
func vulnerableComponentsTest(t *testing.T, ts *httptest.Server) {
	component := ComponentInfoRequest{
		Components: []Component{
			{ComponentID: "pypi://requests:2.22.0"},
			{ComponentID: "vulnerable://component:1.0.0"},
		},
		Context: "foo",
	}
	data, err := json.Marshal(component)
	if err != nil {
		t.Fatal(err)
	}
	req, err := http.NewRequest("GET", ts.URL+"/api/componentinfo", bytes.NewBuffer(data))
	if err != nil {
		t.Fatal(err)
	}
	req.Header.Set("apiKey", apiKey)
	req.Header.Set("Content-Type", "application/json")
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		t.Fatal(err)
	}
	if resp.StatusCode != http.StatusOK {
		t.Error("Expected 200 status, received: ", resp.StatusCode)
	}
	defer resp.Body.Close()
	componentInfo := ComponentInfoResponse{}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		t.Fatal(err)
	}
	err = json.Unmarshal(body, &componentInfo)
	if err != nil {
		t.Fatal(err)
	}
	if len(componentInfo.Components) != 2 {
		t.Error("Unable to find component with id: ", component.Components[0].ComponentID, " in db file.")
	}
	if len(componentInfo.Components[0].Vulnerabilities) != 1 || len(componentInfo.Components[0].Vulnerabilities) != 1 {
		t.Error("Expected to find 2 vulnerabilities.")
	}
}

// Valid api keys
// should result in a 200 status response with the expected {Valid: true} json body
func validAPIKeyTest(t *testing.T, ts *httptest.Server) {
	req, err := http.NewRequest("GET", ts.URL+"/api/checkauth", nil)
	if err != nil {
		t.Fatal(err)
	}
	req.Header.Set("apiKey", apiKey)
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		t.Fatal(err)
	}
	defer resp.Body.Close()
	data := CheckAuthResponse{}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		t.Fatal(err)
	}
	err = json.Unmarshal(body, &data)
	if err != nil {
		t.Fatal(err)
	}
	if !data.Valid {
		t.Error("Failed to validate api key: ", apiKey)
	}
}

// Invalid api keys
// should result in a 200 status response with the expected {Valid: false, Error: "Invalid Api Key"} json body.
func invalidAPIKeyTest(t *testing.T, ts *httptest.Server) {
	req, err := http.NewRequest("GET", ts.URL+"/api/checkauth", nil)
	if err != nil {
		t.Fatal(err)
	}
	invalidKey := "invalidAPIKey"
	req.Header.Set("apiKey", invalidKey)
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		t.Fatal(err)
	}
	defer resp.Body.Close()
	data := CheckAuthResponse{}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		t.Fatal(err)
	}
	err = json.Unmarshal(body, &data)
	if err != nil {
		t.Fatal(err)
	}
	if data.Valid {
		t.Error("Invalid API key was accepted: ", invalidKey)
	}
	if data.Error != InvalidAPIKeyMessage {
		t.Error("Expected message: '"+InvalidAPIKeyMessage+"'. Got: '", data.Error, "'")
	}
}
