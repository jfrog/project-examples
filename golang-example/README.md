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
* Create a remote Go repository named *go-remote*.
* Create a virtual Go repository named *go-virtual*. 
* Create a local Go repository named *go-local*. 
* Include *go-remote* and *go-local* in *go-virtual*.

### Build the Project Using JFrog CLI
CD to the root project directory

```console
Configure Artifactory:
> jfrog rt c

We'll build this project only once with the --no-registry option, to bypass Artifactory.
We're bypassing Artifactory to fetch the project dependencies from github.
> jfrog rt go build --no-registry

Now that we fetched the project dependencies from github, let's push them to Artifactory.
Future builds will not need to run this command and also the previous one.
> jfrog rt gp go-local --self=false --deps=ALL

Build the project with vgo and resolve the project dependencies from Artifactory.
> jfrog rt go build go-virtual 

Publish the package we build to Artifactory.
> jfrog rt gp go-local v1.0.0 --build-name=my-build --build-number=1

Collect environment variabkes and add them to the build info.
> jfrog rt bce my-build 1

Publish the build info to Artifactory.
> jfrog rt bp my-build 1
```
