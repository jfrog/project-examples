# Jfrog webhook blog post

brew install jfrog-cli

## server
go run serve.go

## artifactory

//build image
docker build . -t localhost:8082/docker-local-staging/helloworld
//push to artifactory
jfrog rt docker-push localhost:8082/docker-local-staging/helloworld docker-local-staging --url http://localhost:8082/artifactory --user admin --password password
//promote
jfrog rt docker-promote helloworld docker-local-staging docker-local-prod --user admin --password password --url http://localhost:8082/artifactory --copy
//get image on web server
jfrog rt docker-pull localhost:8082/docker-local-prod/helloworld:latest docker-local-prod  --user admin --password password --url http://localhost:8082/artifactory
//run it
docker run --name helloworld_app -p 8080:8080 localhost:8082/docker-local-prod/helloworld:latest

## webhook