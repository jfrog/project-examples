## Gradle Artifactory Plugin Examples

### Overview
The Gradle Artifactory Plugin allows you to deploy your build artifacts and build information to Artifactory and also to resolve
your build dependencies from Artifactory.
The Plugin documentation is available [here](https://www.jfrog.com/confluence/display/RTF/Gradle+Artifactory+Plugin).
We have included a few sample projects to help you get started using the plugin.

### Plugin Versions
Version 4.0.0 of the Gradle Artifactory Plugin has recently been released.
In addition to a few enhancements and improvements version 4 also includes the following change.
Before version 4, the plugin included two plugin IDs: *com.jfrog.artifactory-upload* for the usage of Gradle Configurations
and *com.jfrog.artifactory* for the usage of Gradle Publications.
To make it easier to configure the plugin, version 4 has removed the *com.jfrog.artifactory-upload* plugin ID, leaving only one plugin ID: *com.jfrog.artifactory*. This allows you to use both Configurations and Publications in the same build script.
We therefore split the Gradle examples 
into [Version 4 Examples](https://github.com/JFrogDev/project-examples/tree/master/gradle-examples/4) 
and [Version 3 Examples](https://github.com/JFrogDev/project-examples/tree/master/gradle-examples/3).  

### A note about publishing.
These examples assume that you are running artifactory on localhost at port 8081.
However, they have a property that allows you to point at a different artifactory URL.

You can pass -Partifactory_url=http://artifactory.your.company:8081/artifactory to point at a different artifactory server.
If you're using artifactory SAAS, the url is a little bit different, it'll be -Partifactory_url=https://example.jfrog.io/example
Notice that the url ends with the name of your artifactory instance instead of /artifactory when using artifactory SAAS.

