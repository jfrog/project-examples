# Yarn Berry example

## Overview
Yarn Berry repositories are supported by Artifactory.
To work with Yarn repositories you need to use [JFrog CLI](https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory) and having your Yarn project configured to an Artifactory server.

## Prerequisite
* Install version 2.4.0 or above of **yarn**.
* Make sure the **yarn** command is in your PATH (you can check by executing 'yarn --version' and get a version number as a response).
* Install [JFrog CLI](https://jfrog.com/getcli/)
* Make sure your JFrog CLI version is 2.20.0 or above

## Running the Example
'cd' to the root project directory

```console
Configure Artifactory:
> jf c add --url=<JFROG_PLATFORM_URL> [credentials flags]

Configure the project's repositories:
> jf yarn-config --repo-resolve=<YARN_RESOLVE_REPO>

Build the project with yarn and resolve the project dependencies from Artifactory.
> jf yarn install --build-name=my-build --build-number=1 

Collect environment variables and add them to the build info.
> jf rt bce my-build 1

Publish the build info to Artifactory.
> jf rt bp my-build 1

Execute 'audit' scan command.
> jf audit
```
