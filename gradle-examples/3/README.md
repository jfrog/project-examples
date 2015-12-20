## Gradle Artifactory Plugin Version 3 Examples
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

### gradle-example-ci-server-bintray-info
Same as *gradle-example-ci-server*, but also creates a bintray-info descriptor file and adds to the build artifacts
to be deployed to Artifactory, so that the build artifacts can be later published to Bintray from Artifactory.

### gradle-example
Sample project that uses the Gradle Artifactory Plugin with Gradle Configurations.

### gradle-example-publish
Sample project that uses the Gradle Artifactory Plugin with Gradle Publications.

### gradle-android-aar
Sample project that uses the Gradle Artifactory Plugin to deploy Android artifacts to Artifactory.

### gradle-example-bintray-info
Sample project that uses the Gradle Artifactory Plugin with Gradle Configurations.
The project creates a bintray-info descriptor file and adds to the build artifacts to be deployed to Artifactory,
so that the build artifacts can be later published to Bintray from Artifactory.

### gradle-example-publish-bintray-info
Sample project that uses the Gradle Artifactory Plugin with Gradle Publications.
The project creates a bintray-info descriptor file and adds to the build artifacts to be deployed to Artifactory,
so that the build artifacts can be later published to Bintray from Artifactory.

### gradle-jcenter-resolve
Resolves a dependency from jcenter.
