## Gradle Artifactory Plugin Examples

### Overview
The Gradle Artifactory Plugin allows you to deploy your build artifacts and build information to Artifactory also resolve
your build dependencies for Artifactory.
The Plugin documentation is available [here](https://www.jfrog.com/confluence/display/RTF/Gradle+Artifactory+Plugin)
We have included a few sample projects to help you get started using the plugin.

### Plugin Versions
Version 4.0.0 of the Gradle Artifactory Plugin has been recently released.
Before version 4, the plugin included two plugin IDs: *com.jfrog.artifactory-upload* when using Gradle Configurations
and *com.jfrog.artifactory* when using Gradle Publications.
To make it easier to configure the plugin, version 4 has only one plugin ID: *com.jfrog.artifactory* allowing you to use both Configurations and Publication in the same build script.
We therefore split the Gradle examples 
into [version 3 examples](https://github.com/jfrogde/project-examples/tree/master/gradle-examples/3) 
and [version 4 examples](https://github.com/jfrogde/project-examples/tree/master/gradle-examples/4).  
