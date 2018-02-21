# Gradle Artifactory Java Client Example

## Introduction
This example provides a project structure for using the Artifactory Java Client in a gradle project.
This code of this project sets up Artifactory, create new repositories, and perform basic
operations - uploading, downloading and searching artifacts using the Java Client.

## Using this example
* Clone the project and open it in your IDE.
* Open *ClientExample.java* and add the URL and credentials of your running Artifactory instance:
```
//TODO: Insert your Artifactory URL and credentials
private static String userName = "<YOUR_USERNAME>";
private static String password = "<YOUR_PASSWORD>";
private static String artifactoryUrl = "<ARTIFACTORY_URL>";
```
* Build and Run the main method of the *ClientExample* class.