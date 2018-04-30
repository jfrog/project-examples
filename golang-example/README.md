# Golang example

## Overview
Go repositories are supported by Artifactory since version 5.11.0.
To work with Go repositories you need to use [JFrog CLI](https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory) and [vgo](https://research.swtch.com/vgo).
This sample project demonstrates how to work with Go repositories in Artifactory.

## Prerequisite
* Install vgo. Read more about vgo [here](https://research.swtch.com/vgo).
* Install [JFrog CLI](https://jfrog.com/getcli/)
* Make sure your Artifactory version is 5.11.0 or above
* Make sure your JFrog CLI version is 1.15.0 or above

## Running the Example
### Create Go Repositories in Artifactory
*Create a remote Go repository named *go-remote*.
*Create a virtual Go repository named *go-virtual*. 
*Create a local Go repository named *go-local*. 
*Include *go-remote* and *go-local* in *go-virtual*.

### Build the Project Using JFrog CLI
CD to the root project directory

```console
Configure Artifactory:
> jfrog rt c

Build the project with vgo and resolve artifacts from Artifactory:
> jfrog rt go build go-virtual 

Publish to Artifactory with the dependencies
> jfrog rt gp go-local v1.0.0 --deps=ALL --build-name=my-build --build-number=1

Publish the build info to Artifactory
> jfrog rt bp my-build 1
```