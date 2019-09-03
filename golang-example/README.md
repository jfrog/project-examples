# Golang example

## Overview
Go repositories are supported by Artifactory since version 5.11.0.
To work with Go repositories you need to use [JFrog CLI](https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory) and the Go client.

## Prerequisite
* Install version 1.11 or above of **go**.
* Make sure the **go** command is in your PATH.
* Install [JFrog CLI](https://jfrog.com/getcli/)
* Make sure your Artifactory version is 5.11.0 or above
* Make sure your JFrog CLI version is 1.26.0 or above

## Running the Example
CD to the root project directory

```console
Configure Artifactory:
> jfrog rt c

Configure the project's repositories:
> jfrog rt go-config

Build the project with go and resolve the project dependencies from Artifactory.
> jfrog rt go build --build-name=my-build --build-number=1 

Publish version v1.0.0 of the package to the go-local repository in Artifactory.
> jfrog rt gp go-local v1.0.0 --build-name=my-build --build-number=1

Collect environment variables and add them to the build info.
> jfrog rt bce my-build 1

Publish the build info to Artifactory.
> jfrog rt bp my-build 1
```

Learn about [building go packages](https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory#CLIforJFrogArtifactory-BuildingGoPackages) and about Artifactory and [go registry](https://jfrog.com/integration/go-registry/) integration.
