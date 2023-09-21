# Yarn Classic example

## Overview
Yarn Classic repositories are partially supported by Artifactory.
To work with Yarn repositories you need to use [JFrog CLI](https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory) and having your Yarn project configured to an Artifactory server.

## Prerequisite
* Install classic version of **yarn** (prior to 2.0.0).
* Make sure the **yarn** command is in your PATH (you can check by executing 'yarn --version' and get a version number as a response).
* Install [JFrog CLI](https://jfrog.com/getcli/)
* Make sure your JFrog CLI version is 2.45.0 or above

## Running the Example
'cd' to the root project directory

```console
Configure Artifactory:
> jf c add --url=<JFROG_PLATFORM_URL> [credentials flags]

Configure the project's repositories:
> jf yarn-config --repo-resolve=<YARN_RESOLVE_REPO>

Execute 'audit' scan command.
> jf audit
```

### Note
* Building projects is currently unsupported for Yarn Classic projects


