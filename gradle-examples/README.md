## Gradle Artifactory Plugin Examples
Sample projects that use the Gradle Artifactory Plugin.
The plugin adds the "artifactoryPublish" task.
The Plugin documenation is available at:
https://www.jfrog.com/confluence/display/RTF/Gradle+Artifactory+Plugin

#### Running the examples
```console
> gradle artifactoryPublish

or with the gradle wrapper in Unix

> ./gradlew artifactoryPublish

and the gradle wrapper in Windows

> gradlew.bat artifactoryPublish
```

### gradle2-example-ci-server
Gradle 2.x sample project to be used with one of the Artifactory CI Server Plugins (Jenkins, TeamCity or Bamboo).
The Artifactory configuration in this case (repositories, Artifactory credentials, etc.)
is done from the CI Server UI.
You can still add the artifactory closure to the build script and have default values configured there,
but the values configured in the CI Server override them.
In this example, the only Artifactory property configured is "artifactoryPublish.skip = true".

#### Important notes for using this example from a CI Server:

* Make sure to have the "Project uses the Artifactory Gradle Plugin" check box in the CI Server UI as
unchecked, so that the CI Server Plugin automatically applies the Gradle Artifactory Plugin to your
build script.
* In order to publish the build artifacts to Artifactory, the published arrtifacts are added to the archives Gradle configuration.

### gradle2-example
Gradle 2.x sample project that uses Gradle Configurations.

### gradle2-example-publish
Gradle 2.x sample project that uses Gradle Publications.

### gradle-example
Gradle 1.x sample project that uses Gradle Configurations.

### gradle-example-publish
Gradle 1.x sample project that uses Gradle Publications.

### gradle-android-aar
Sample project that uses the Gradle Artifactory Plugin to deploy Android artifacts to Artifactory.

### gradle-jcenter-resolve
Resolves a dependency from jcenter.
