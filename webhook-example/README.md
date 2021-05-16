# Jfrog Webhook example
This project is a code sample to show you how to create a Webhook handler for Artifactory.
Here is the related blog post

## server
Server is the code snippet for the web application we want to update. It's written in golang. There is 2 files :
- serve.go : the simple http server 
- Dockerfile : the Dockerfile for building an image for this web app

## webhook
Webhook is the code snippet for updating the running docker image of the web app :
- serve.go : the webhook handler

You can run it with 

```golang
go run serve.go
```

## Build/Deploy Process

here are the command for running this example

Run artifactory locally
```bash
docker run --name artifactory -d -p 8081:8081 -p 8082:8082 releases-docker.jfrog.io/jfrog/artifactory-pro:latest
```

(here you need to setup artifactory repository and webhook, install Jfrog CLI, follow the instructions in the blog post)

Build the image
```bash
(cd server && docker build . -t localhost:8082/docker-local-staging/helloworld)
```

Push image to artifactory
```bash
jfrog rt docker-push localhost:8082/docker-local-staging/helloworld docker-local-staging --url http://localhost:8082/artifactory --user admin --password password
```

Promote the image
```bash
jfrog rt docker-promote helloworld docker-local-staging docker-local-prod --user admin --password password --url http://localhost:8082/artifactory --copy
```
