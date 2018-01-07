# Maven Artifactory Java Client Example

## Introduction
This example provides a project structure for using the Artifactory Java Client in a maven project.
In this project you will find how to set up Artifactory, create new repositories, and perform basic
operations - upload, download and search artifacts using the Java Client.

## Using this example
* Download the project to your system and open it in your IDE.
* Open `ClientExample.java` and insert the URL and credentials to your running Artifactory:
```
//TODO: Insert your Artifactory URL and credentials
private static String userName = "<YOUR_USERNAME>";
private static String password = "<YOUR_PASSWORD>";
private static String artifactoryUrl = "<ARTIFACTORY_URL>";
```
* Build and Run the solution