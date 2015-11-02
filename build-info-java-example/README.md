# build-info-java-example
This example project demonstrate the ability to create an Artifactory [build-info OSS project](https://github.com/JFrogDev/build-info)
, deploy it to Artifactory and deploy the build artifact from a filesystem directory to Artifactory by using the build-info OSS project, similarly to what the CI/build tools Artifactory plugins does.

### Motivation
JFrog's Artifactory plugins (CI/build tools plugins) are very popular, and most of the people who are using Artifactory to host and manage their binaries with a common CI server/build tool that has a plugin for Artifactory are using it. Still, there are many users which are not using CI server, or using an internal CI server/build tool that has no Artifactory plugin for it which are interested having the build-info benefits and the ability to promote an entire build entity with a single command. These users can use our OSS build-info object to create and deploy their own build info, as we show in this project example

### Notes
This project includes one Java class: 'CreateAndDeploy.java' and uses one dependency: 'build-info-extractor'. 
The 'CreateAndDeploy' main method builds an Artifactory build-info object and deploys it to Artifactory. Our example collects the build artifacts information (artifacts name, checksums) from a directory which should be passed to the main method 'String[] args' array. In our example, the build-info object includes one module (but can include more), and the module should have one or more artifacts. Once the information was collected, our script will deploy it to Artifactory (it can also deploy the actual artifact).


## Getting Started
You can either run this from your IDE, or by command line. 
In order to run it from your IDE, just import the project, resolve the 'build-info-extractor' dependency with Maven, and run the main method.
Note, you should pass your build artifacts directory path as the first element of the main arguments array ('String[] args'). You will most likely be interested to change the 'buildName', 'buildNumner', 'targetRepository' and 'artifactoryURL' values.

In order to run it from the command line, please follow the below instructions:  
First, clone the code:
```
git clone git@github.com:JFrogDev/project-examples.git
```
Once you have the code on your machine, run:
```mvn install```
Maven will create an uber-jar named 'uber-buildCreatorExample-1.0-SNAPSHOT.jar' under the 'target' directory. Go to the 'target' and run:
```java -jar /path/to/buildArtifactsFolder/```




