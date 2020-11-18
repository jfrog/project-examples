package main

import (
	"context"
	"encoding/base64"
	"encoding/json"
	"github.com/docker/docker/api/types"
	"github.com/docker/docker/api/types/container"
	"github.com/docker/docker/client"
	"github.com/docker/go-connections/nat"
	"io/ioutil"
	"log"
	"net/http"
)

const (
	imageName     = "localhost:8082/docker-local-prod/helloworld:latest"
	containerName = "helloworld_app"
)

type DockerEventPayload struct {
	Domain    string `json:"domain"`
	EventType string `json:"event_type"`
	Data      struct {
		ImageName string `json:"image_name"`
		Name      string `json:"name"`
		Path      string `json:"path"`
		Platforms []struct {
			Architecture string `json:"architecture"`
			Os           string `json:"os"`
		} `json:"platforms"`
		RepoKey string `json:"repo_key"`
		Sha256  string `json:"sha256"`
		Size    int    `json:"size"`
		Tag     string `json:"tag"`
	} `json:"data"`
}

func main() {
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		ctx := context.Background()
		p, err := readPayload(r)
		if err != nil {
			http.Error(w, err.Error(), http.StatusBadRequest)
			log.Printf("Payload reading error: %+v", err)
			return
		}
		if !isMyServerEvent(r, p) {
			http.Error(w, "Bad event", http.StatusBadRequest)
			log.Printf("Unexpected event %+v", p)
			return
		}
		cli, err := client.NewClientWithOpts(client.FromEnv, client.WithAPIVersionNegotiation())
		if err != nil {
			log.Printf("New client error: %+v", err)
			return
		}
		err = pullLatestVersion(cli, ctx)
		if err != nil {
			log.Printf("Pull error: %+v", err)
			return
		}
		err = stopRunningContainer(cli, ctx)
		if err != nil {
			if client.IsErrNotFound(err) {
				log.Printf("Container does not exists")
			} else {
				log.Printf("Stop error: %+v", err)
				return
			}
		}
		err = startContainer(cli, ctx)
		if err != nil {
			log.Printf("Start error: %+v", err)
		} else {
			log.Printf("Container updated ")
		}
	})
	http.ListenAndServe(":7979", nil)
}

func startContainer(cli *client.Client, ctx context.Context) error {
	resp, err := cli.ContainerCreate(ctx,
		&container.Config{
			Image: imageName,
		},
		&container.HostConfig{
			PortBindings: nat.PortMap{
				"8080/tcp": []nat.PortBinding{
					{
						HostIP:   "0.0.0.0",
						HostPort: "8080",
					},
				},
			},
		}, nil, nil, containerName)
	if err != nil {
		return err
	}
	err = cli.ContainerStart(ctx, resp.ID, types.ContainerStartOptions{})
	if err != nil {
		return err
	}
	return nil
}

func stopRunningContainer(cli *client.Client, ctx context.Context) error {
	return cli.ContainerRemove(ctx, containerName, types.ContainerRemoveOptions{Force: true})
}

func pullLatestVersion(cli *client.Client, ctx context.Context) error {
	authConfig := types.AuthConfig{
		Username: "admin",
		Password: "password",
	}
	encodedJSON, _ := json.Marshal(authConfig)
	_, err := cli.ImagePull(ctx, imageName, types.ImagePullOptions{RegistryAuth: base64.URLEncoding.EncodeToString(
		encodedJSON)})
	if err != nil {
		log.Printf("Pull error: %+v", err)
		return err
	}
	return nil
}

func isMyServerEvent(r *http.Request, p DockerEventPayload) bool {
	return p.Domain == "docker" &&
		p.EventType == "promoted" &&
		p.Data.ImageName == "helloworld" &&
		p.Data.RepoKey == "docker-local-staging" &&
		p.Data.Tag == "latest" &&
		r.Header.Get("X-JFrog-Event-Auth") == "mysecrets"
}

func readPayload(r *http.Request) (DockerEventPayload, error) {
	bodyBytes, err := ioutil.ReadAll(r.Body)
	if err != nil {
		return DockerEventPayload{}, err
	}
	var p DockerEventPayload
	err = json.Unmarshal(bodyBytes, &p)
	if err != nil {
		return DockerEventPayload{}, err
	}
	return p, nil
}
