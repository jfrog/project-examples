# Jenkins Pipeline - Working with Artifactory

## Introduction
The Pipeline Jenkins Plugin allows you to create a script that defines your build steps. 
For those of you who are not familliar with Jenkins Pipeline, please check out the [Pipeline tutorial](https://github.com/jenkinsci/pipeline-plugin/blob/master/TUTORIAL.md) or the [Getting started with Pipeline](https://jenkins.io/doc/pipeline/) documentation.
We have recently added support for Artifactory operations as part of the Pipeline script DSL. 
You can now download dependencies, upload artifacts and publish build-info to Artifactory from a Pipeline script.
The Pipeline support for Artifactory is provided by the [Jenkins Artifactory Plugin](https://github.com/JFrogDev/jenkins-artifactory-plugin). 
The official release of the Jenkins Artifactory Plugin with Pipeline support hasn't been published yet,
but it should become available soon.
We enccourage you however to try out the plugin snapshot.

## Installing the Plugin Snapshot
1. Make sure that the Pipeline Plugin is installed on your Jenkins instance.
2. Download the the Artifactory Plugin snapshot from [here](https://bintray.com/jfrog/jfrog-jars/download_file?file_path=artifactory.hpi).
3. Install the Artifactory Plugin you downloaded by following these steps:
⋅⋅1. Go to ‘Manage Jenkins’ --> ‘Manage Plugins’ --> ‘Advanced’ tab.
⋅⋅2. In the ‘Upload Plugin’ section, select the path to the artifactory.hpi file and click ‘Upload’
⋅⋅3. Restart Jenkins

## Using the Artifactory DSL
### Creating an Artifactory Server Instance
In order to download or upload files from and to your Artifactory server, you first
need to create an Artifactory server instance in your PipeLine script. 
If your Artifactory server is already defined in Jenkins, all you need is the server ID. 
To get or set your Artifactory server ID, go to Manage --> Configure System.
Then, you can create your Artifactory server instance by adding the following line to your script:

def server = Artifactory.server('my-server-id')

### Downloading and Uploading Files From and To Artifactory
In order to download files from an Artifactory server, we first need to create a spec, 
which is a json string. 
The spec defines the files that you'd like to download and the target path for the files. 
Here's an example:
```
def downloadSpec = '{
 "files": [
  {
      "pattern": "bazinga-repo/*.zip",
      "target": "bazinga/"
    }
 ]
}'
```
The above spec defines the following:
Downloads all zip files in the *bazinga-repo* Artifactory repository into the 
bazinga directory on your Jenkins agent file-system.
Note that *files* is a list, so your spec can include a list of patterns and targets.
The next step is to use the spec to download the files. You do that by adding the following line:
```
server.download(downloadSpec)
```
That's it. Easy isn't it?

The concept of uploading files is similar to downloading. First create a spec and then use it upload files to the Arttifactory server:
```
def uploadSpec = '{
  "files": [
    {
      "pattern": "bazinga/*froggy*.zip",
      "target": "bazinga-repo/froggy-files"
    }
 ]
}'
server.upload(uploadSpec)
```
The above code uploaads all zip files that include *froggy* in their names into the *froggy-files* foldder in the *bazinga-repo* Artifactory repository.

### Publishing Build-Info to Artifactory
Both the *download* and *upload* methods return a build-info object, that you can then 
publish to Artifactory. Here's how you do it:
```
def buildInfo1 = server.download(downloadSpec)
def buildInfo2 = server.upload(uploadSpec)
buildInfo1.append(buildInfo2)
server.publishBuildInfo(buildInfo1)
```
You can also do the following:
```
def buildInfo = Artifactory.newBuildInfo()
server.download(artifactoryDownloadDsl, buildInfo)
server.upload(artifactoryDownloadDsl, buildInfo)
server.publishBuildInfo(buildInfo1)
```

## Advanced Upload Options
### Using Placeholders When Uploading
The upload spec enormous flexibility in how you define the target path, through the use of wildcard or regular expressions with placeholders.
Any wildcard enclosed in parenthesis in the source path can be matched with a corresponding placeholder in the target path to determine the name of the artifact once uploaded.
In the following example, for each .tgz file in the source directory, a corresponding directory with the same name 
is created in the target repository and the file is uploaded into it. 
For example, a file called froggy.tgz should be uploaded to my-local-rep/froggy/froggy.tgz.
```
def uploadSpec = '{
  "files": [
    {
      "pattern": "(*).tgz",
      "target": "my-local-repo/{1}/"
      "recursive": "false"
    }
 ]
}'
```
### Using Regular Expressions
You can use a regular expression in your upload spec to define which artifacts should be uploaded.
You need to add *"regexp": "true"* to the spec.
Here's an example:
```
def uploadSpec = '{
  "files": [
    {
      "pattern": "(.*).tgz",
      "target": "my-local-repo/{1}/"
      "recursive": "false"
      "regexp": "true"
    }
 ]
}'
```

## The Download Spec Schema
You can also use [AQL](https://www.jfrog.com/confluence/display/RTF/Artifactory+Query+Language) instead of the wildcards pattern:
```
{
  "files": [
    {
      "pattern" or "aql": "[Mandatory]",
      "target": "[Mandatory]",
      "recursive": "[Optional]",
      "flat" : "[Optional]",
      "props": "[Optional]"
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
      "recursive": "[Optional]",
      "flat" : "[Optional]",
      "props": "[Optional]"
      "regexp": "[Optional]"
    }
  ]
}
```
## Examples
We included a few examples to help you get started using the Artifactory DSL in your Pipeline scripts.

The *props-example* downloads and uploads files to Artifactory with properties. 
It also uses a placeholder when uploading. 