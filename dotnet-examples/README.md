# .NET examples

## Overview
Artifactory provides full support for managing .NET/NuGet packages and ensures optimal and reliable access to nuget.org. It also allows aggregating multiple NuGet registries under a virtual repository Artifactory, which provides access to all your .NET/NuGet packages through a single URL for both upload and download.
To work with NuGet repositories you need to use [JFrog CLI](https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory) and have your .Net/NuGet project configured to an Artifactory server.

You may store exhaustive build information in Artifactory by running your .NET/NuGet builds with JFrog CLI. JFrog CLI collects build-info from your build agents and then publishes it to Artifactory. Once published, the build info can be viewed in the Build Browser under Builds. For more details on .NET/NuGet build integration using JFrog CLI, please refer to [Building .NET/NuGet Packages](https://jfrog.com/help/r/jfrog-cli/building-nuget-packages) in the JFrog CLI User Guide

## These Examples 
These examples demonstrate how to build a .NET/NuGet project using JFrog CLI. The build does the following:

1. Downloads its NuGet dependencies from Artifactory.
2. Packs and uploads the built package to Artifactory.
3. Records and publishes build-info to Artifactory.

## Prerequisites
* Install .NET Core 3.1 SDK or a later version - for full support you must have .NET CLI on your machine
* Make sure the **dotnet** command is in your PATH (you can check by executing 'dotnet --version' and get a version number as a response).
* Install [JFrog CLI](https://jfrog.com/getcli/)
* Make sure your JFrog CLI version is 2.25.3 or above

# Creating Repositories
Create the following repositories on your Artifactory instance:

1. A remote NuGet repository. Make sure the repository has *https://www.nuget.org/* configured as its URL (this is the default when creating the repository).
2. A local NuGet repository.
3. A virtual NuGet repository:
    - Include the remote and local repositories as part of the new virtual repository.
    - Set the new local repository as the **Default Deployment Repository** of the new virtual repository.

## Running the Examples
'cd' to one of the project's root directory and run the build using one of the following commands:

```console
Configure Artifactory:
> jf c add --url=<JFROG_PLATFORM_URL> [credentials flags]

Configure the project's repositories:
> jf dotnet-config --repo-resolve=<NUGET_RESOLUTION_REPOSITORY>

Restores the dependencies and tools of the project from Artifactory
> jf dotnet restore --build-name=my-build --build-number=1

Execute 'audit' scan command.
> jf audit

Build the project with dotnet and resolve the project dependencies from Artifactory.
> dotnet pack

Upload the packages to a NuGet repository in Artifactory:
> jf rt u "*.nupkg" <NUGET_DEPLOYMENT_REPOSITORY>/ --build-name=my-build --build-number=1

Collect environment variables and add them to the build info.
> jf rt bce my-build 1

Publish the build info to Artifactory.
> jf rt bp my-build 1
```
