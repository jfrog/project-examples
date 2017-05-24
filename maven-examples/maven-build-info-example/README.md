# build-info-java-example
This project is an example that demonstrates the ability to create an Artifactory build-info object, deploy it to Artifactory and deploy the build artifact from a filesystem directory to Artifactory by using the [build-info OSS project](https://github.com/JFrogDev/build-info), similar to what the CI/build tools Artifactory plugins do.


### Motivation
JFrog's Artifactory plugins for CI/build tools are very popular. Most users who host and manage their binaries in Artifactory together and use a common CI server or build tool that has a corresponding Artifactory plugin use them. Still, there are many users who don’t use a CI server, or use an internal CI server/build tool that does not have an Artifactory plugin. These users would still like to benefit from Artifactory’s comprehensive build-info and the ability to promote an entire build entity with a single command. These users can use our OSS build-info object to create and deploy their own build info, as we demonstrate in this project.

### Notes
The project includes one Java class, ``CreateAndDeploy.java``, and uses one dependency: ``build-info-extractor``. The 'CreateAndDeploy' main method builds an Artifactory build-info object and deploys it to Artifactory. Our example collects information on the build artifacts (artifact names, checksums etc.) from a directory which should be passed to the main method’s 'String[] args' array. In our example, the build-info object includes one module (but can include more) which should have one or more artifacts. Once the information is collected, our script will deploy it to Artifactory (it can also deploy the actual artifact).

## Getting Started
You can either run this from your IDE, or on a command line. To run it from your IDE, just import the project, resolve the 'build-info-extractor' dependency with Maven, and run the main method. Note, you should pass your build artifacts directory path as the first element of the main arguments array ('String[] args'). You will most likely want to change the 'buildName', 'buildNumber', 'targetRepository' and 'artifactoryURL' values.

In order to run it from the command line, please follow the below instructions:  
First, clone the code:
```
git clone git@github.com:JFrogDev/project-examples.git
```
Once you have the code on your machine, run:
```mvn install```
Maven will create an uber-jar named 'uber-buildCreatorExample-1.0-SNAPSHOT.jar' under the 'target' directory. Go to the 'target' directory and run:
```
java -jar /path/to/buildArtifactsFolder/
```




