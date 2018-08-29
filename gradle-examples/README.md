# Gradle Artifactory Plugin Examples

## Overview
The Gradle Artifactory Plugin allows you to deploy your build artifacts and build information to Artifactory and also to resolve
your build dependencies from Artifactory.
The Plugin documentation is available [here](https://www.jfrog.com/confluence/display/RTF/Gradle+Artifactory+Plugin).
We have included a few sample projects to help you get started using the plugin.

## Running the Examples
* The example projects are configured to work with an Artifactory instance accessed through the following URL:<br>
http://localhost:8081/artifactory<br>
This URL is defined inside the *build.gradle* file of each project. Please change it if your Artifactory instance is accessible through a different URL.
* Since all example projects are configured to resolve dependencies from 'jcenter', it is recommended to create a remote repository named *jcenter*, with *https://jcenter.bintray.com* as its URL.  
* CD to one of the project's root directory and run the build using one of the following commands:

```console
> gradle artifactoryPublish

or with the gradle wrapper in Unix

> ./gradlew artifactoryPublish

and the gradle wrapper in Windows

> gradlew.bat artifactoryPublish
```

## About the Examples
### gradle-example-minimal
A minimal sample project that uses the Gradle Artifactory Plugin to resolve and publish artifacts to Artifactory.

### gradle-example-ci-server
Gradle sample project to be used with one of the Artifactory CI Server Plugins (Jenkins, TeamCity or Bamboo).
The Artifactory configuration in this case (repositories, Artifactory credentials, etc.)
is done from the CI Server UI.
You can still add the artifactory closure to the build script and have default values configured there,
but the values configured in the CI Server override them.
In this example, the only Artifactory property configured is "artifactoryPublish.skip = true".

#### Important notes for using this example from a CI Server:

* Make sure to have the "Project uses the Artifactory Gradle Plugin" check box in the CI Server UI unchecked, so that the CI Server Plugin automatically applies the Gradle Artifactory Plugin to your
build script.
* In order to publish the build artifacts to Artifactory, the published arrtifacts are added to the archives Gradle configuration.

### gradle-example
Sample project that uses the Gradle Artifactory Plugin with Gradle Configurations.

### gradle-example-publish
Sample project that uses the Gradle Artifactory Plugin with Gradle Publications.

### gradle-android-example
Sample project that uses the Gradle Artifactory Plugin to deploy Android application(apk) and library(aar) to Artifactory.

Compatible with Android gradle plugin version 3.0.x

### gradle-jcenter-resolve
Resolves a dependency from jcenter.

### gradle-cache-example
Simple copy of the `gradle-example` project with modified configuration to use Artifactory as an external 
Gradle Build Cache. This feature was introduced with Gradle 3.5.
Please see https://docs.gradle.org/3.5/userguide/build_cache.html for more details.
To make it work, you'll need to create a generic repository in Artifactory called `gradle-cache-example`.
If you need to tweak the repo name or credentials, you can change them in `settings.gradle`.
The first time you should build this project with:
 `./gradlew clean build --info -Pgradle.cache.push=true`
After downloading the correct Gradle version, it will take about about 11s and push the cache to Artifactory. During 
tests execution, you should see the message `Executing heavy fake test`. 
Then if you try it from a different environment, or simply rebuild with `./gradlew clean build`, 
it will skip the test task, fetch the cache from Artifactory instead and should take about 1s.

Read more information about [Artifactory as a Gradle repository](https://jfrog.com/integration/gradle-repository/) and the [Gradle Artifactory Plugin](https://www.jfrog.com/confluence/display/RTF/Gradle+Artifactory+Plugin).
Also read about [JFrog CLI's integration with Gradle and Maven](https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory#CLIforJFrogArtifactory-RunningMavenandGradleBuilds).