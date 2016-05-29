# Jenkins Pipeline - Working With Artifactory

## Introduction
The Pipeline Jenkins Plugin allows you to create a script that defines your build steps. 
For those not familiar with Jenkins Pipeline, check out the [Pipeline Tutorial](https://github.com/jenkinsci/pipeline-plugin/blob/master/TUTORIAL.md) or the [Getting Started With Pipeline](https://jenkins.io/doc/pipeline/) documentation.
We recently added support for Artifactory operations as part of the Pipeline script DSL. 
You have the added option of downloading dependencies, uploading artifacts, and publishing build-info to Artifactory from a Pipeline script.
Pipeline support for Artifactory is provided by the [Jenkins Artifactory Plugin](https://github.com/JFrogDev/jenkins-artifactory-plugin). 
The official release of the Jenkins Artifactory Plugin with Pipeline support has not been published yet,
but will become available within the coming weeks.
In the meantime, we encourage you to try out the plugin snapshot.

## Installing the Plugin Snapshot
1. Ensure that the Pipeline Plugin is installed on your Jenkins instance.
2. Download the the Artifactory Plugin snapshot from [this link](https://bintray.com/jfrog/jfrog-jars/download_file?file_path=1.0.1/artifactory.hpi).
3. To install the Artifactory Plugin you downloaded, follow these steps:
  * Go to ‘Manage Jenkins’ --> ‘Manage Plugins’ --> ‘Advanced’ tab.
  * In the ‘Upload Plugin’ section, select the path to the artifactory.hpi file and click ‘Upload’.
  * Restart Jenkins.

## Using the Artifactory DSL
### Creating an Artifactory Server Instance
To upload or download files to and from your Artifactory server, first you need to
create an Artifactory server instance in your Pipeline script. 
If your Artifactory server is pre-defined in Jenkins, all you need is the server ID. 
To obtain or set your Artifactory server ID, go to Manage --> Configure System.
Then, you can create your Artifactory server instance by adding the following line to your script:
```
def server = Artifactory.server('my-server-id')
```
You also have the option of creating an Artifactory instance, even if the server is not pre-defined in Jenkins. To create an Artifactory instance, add the following line to your script:
```
def server = Artifactory.newServer('artifactory-url', 'username', 'password')
```
We recommend using variables, rather than plain text, to specify the Artifactory server details.

### Uploading and Downloading Files To and From Artifactory
Once you have created an Artifactory server instance, you are ready to download files from an Artifactory server. First, you need to create a spec, 
which is a json string. 
The spec defines the files that you want to download and the target path for the files. 
See the example below:
```
def downloadSpec = """{
 "files": [
  {
      "pattern": "bazinga-repo/*.zip",
      "target": "bazinga/"
    }
 ]
}"""
```
The above spec defines the following:
Downloads all zip files in the *bazinga-repo* Artifactory repository into the 
*bazinga* directory on your Jenkins agent file-system.
Notice that *files* is a list, therefore, your spec can include a list of patterns and targets.
The following step is to use the spec to download the files. To download the files, add the following line:
```
server.download(downloadSpec)
```
Piece of cake, right?

The process of uploading files is similar to downloading files. First, create a spec and then use this spec to upload files to the Artifactory server:
```
def uploadSpec = """{
  "files": [
    {
      "pattern": "bazinga/*froggy*.zip",
      "target": "bazinga-repo/froggy-files"
    }
 ]
}"""
server.upload(uploadSpec)
```
The code shown above uploads all zip files that include *froggy* in their names into the *froggy-files* foldder in the *bazinga-repo* Artifactory repository.

### Publishing Build-Info to Artifactory
Both the download and upload methods return a build-info object, which can be published 
to Artifactory. Below is an example code sample:
```
def buildInfo1 = server.download(downloadSpec)
def buildInfo2 = server.upload(uploadSpec)
buildInfo1.append(buildInfo2)
server.publishBuildInfo(buildInfo1)
```
Below is a second example:
```
def buildInfo = Artifactory.newBuildInfo()
server.download(artifactoryDownloadDsl, buildInfo)
server.upload(artifactoryUploadDsl, buildInfo)
server.publishBuildInfo(buildInfo)
```

## Advanced Upload Options
### Using Placeholders When Uploading
The upload spec allows for flexibility in how you define the target path by using wildcard or regular expressions with placeholders.
Any wildcard enclosed in parenthesis in the source path can be matched with a corresponding placeholder in the target path to determine the name of the artifact once uploaded.
In the following example, for each .tgz file in the source directory, a corresponding directory with the same name 
is created in the target repository and the file is created in the corresponding directory. 
For example, a file called froggy.tgz is uploaded to my-local-rep/froggy/froggy.tgz.
```
def uploadSpec = """{
  "files": [
    {
      "pattern": "(*).tgz",
      "target": "my-local-repo/{1}/"
      "recursive": "false"
    }
 ]
}"""
```
### Using Regular Expressions
You have the option of using a regular expression rather than wildcard patterns to define the upload pattern. Add *"regexp": "true"* to the spec. 
See the example below:
```
def uploadSpec = """{
  "files": [
    {
      "pattern": "(.*).tgz",
      "target": "my-local-repo/{1}/"
      "recursive": "false"
      "regexp": "true"
    }
 ]
}"""
```

## The Download Spec Schema
You can also use [AQL](https://www.jfrog.com/confluence/display/RTF/Artifactory+Query+Language) instead of wildcard patterns:
```
{
  "files": [
    {
      "pattern" or "aql": "[Mandatory]",
      "target": "[Mandatory]",
      "props": "[Optional]",
      "recursive": "[Optional, Default: true]",
      "flat" : "[Optional, Default: false]"      
    }
  ]
}
```
## The Upload Spec Schema
```
{
  "files": [
    {
      "pattern": "[Mandatory]",
      "target": "[Mandatory]",
      "props": "[Optional]",
      "recursive": "[Optional, Default: 'true']",
      "flat" : "[Optional, Default: 'true']",
      "regexp": "[Optional, Default: 'false']"
    }
  ]
}
```
## Examples
The examples below are meant to help you get started using the Artifactory DSL in your Pipeline scripts.
You can run the examples by following one of the two methods described below.
In your Jenkins job configuration, set *Definition* to "Pipeline script", 
and then copy the content of the example's Jenkinsfile into the *Script* text-area.
Alternatively, you can do the following:
* Set *Definition* to "Pipeline script from SCM".
* Set *SCM* to *Git*.
* Set *Repository URL* to *https://github.com/JFrogDev/project-examples.git*
* Set *Script Path* to the relative path to the example's Jenkinsfile. For example, for the below *props-example*, set *Script Path* to *jenkins-pipeline-examples/props-example/Jenkinsfile* 

Here are the available examples.
* The [props-example](https://github.com/jfrogdev/project-examples/tree/master/jenkins-pipeline-examples/props-example) downloads and uploads files to Artifactory with properties. The props-example also uses a placeholder when uploading.
* The [props-single-file-example](https://github.com/jfrogdev/project-examples/tree/master/jenkins-pipeline-examples/props-single-file-example) is the same as the [props-example](https://github.com/jfrogdev/project-examples/tree/master/jenkins-pipeline-examples/props-example), but has the specs embedded inside the Groovy script.
* The [aql-example](https://github.com/jfrogdev/project-examples/tree/master/jenkins-pipeline-examples/aql-example) uses [AQL](https://www.jfrog.com/confluence/display/RTF/Artifactory+Query+Language) in its download spec.
