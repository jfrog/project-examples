# Jenkins Pipeline - Working with Artifactory

## Introduction
The Pipeline Jenkins Plugin allows you to create a script that defines your build steps. We have recently added support for Artifactory operations as part of the Pipeline script DSL. You can now download dependencies, upload artifacts and publish build-info to Artifactory from a Pipeline script.<br>
For those of you who are not familliar with Jenkins Pipeline, please check out the [Pipeline tutorial](https://github.com/jenkinsci/pipeline-plugin/blob/master/TUTORIAL.md) or the [Getting started with Pipeline](https://jenkins.io/doc/pipeline/) documentation.<br>
The Pipeline support for Artifactory is provided by the [Jenkins Artifactory Plugin](https://github.com/JFrogDev/jenkins-artifactory-plugin). The official release of the Jenkins Artifactory Plugin with Pipeline support hasn't been published yet, but we enccourage you to try out the plugin snapshot. The official release should become available soon.

## Installing the Plugin Snapshot
1. Make sure that the Pipeline Plugin is installed on your Jenkins instance.
2. Download the the Artifactory Plugin snapshot from [here].
3. Install the Artifactory Plugin you downloaded by following these steps:
3.1. Go to ‘Manage Jenkins’ ? ‘Manage Plugins’ ? ‘Advanced’ tab.
3.2. In the ‘Upload Plugin’ section, select the path to the artifactory.hpi file and click ‘Upload’
3.3. Restart Jenkins

## Using the Artifactory DSL
This plugin adds a new global variable called Artifactory to Pipeline scripts.<br>
The Artifactory variable can be used to define Server object/s.<br>
Each server object represents a single Artifactory server instance and can be used to perform actions against this server instance.<br>
The actions that are currently available are: download, upload and publishBuildInfo.<br>
If you are already familiar with the plugin, you can jump to [running a code sample](#running-a-code-sample).

### Create an Artifactory Server Instance
In order to download or upload files to your Artifactory server, you first need need to create an Artifactory server instance in your PipeLine script. If your Artifactory server is already defined in Jenkins, all you need is the server ID. To get or set your Artifactory server ID, go to Manage --> Configure System.
Then, you can create your Artifactory server instance by adding the following line to your script:

def server = Artifactory.server('my-server-id')

### Downloading and uploading files from and to Artifactory
In order to download files from an Artifactory server, we first need to create Json string that defines the files that we'd like to download and the target path for the files. Here's an example:
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
The above spec downloads all zip files in the *bazinga-repo* Artifactory repository into the bazinga directory on your Jenkins agent file-system. You should then use the spec to download the files as follows:
```
server.download(downloadSpec)
```
That's it. Easy isn't it?
Note that *files* is a list, so your spec can include a list of patterns and targets.

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

### Publishing build-info to Artifactory
Both the *download* and *upload* methods return a build-info thhat you can then publish to Artifactory. Here's how you do it:
```
def buildInfo1 = server.download(downloadSpec)
def buildInfo2 = server.upload(uploadSpec)
buildInfo1.append(buildInfo2)
server.publishBuildInfo(buildInfo1)
```
You can slso the following:
```
def buildInfo = Artifactory.newBuildInfo()
server.download(artifactoryDownloadDsl, buildInfo)
server.upload(artifactoryDownloadDsl, buildInfo)
server.publishBuildInfo(buildInfo1)
```

# Using Placeholders When Uploading
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
server.upload(uploadSpec)
```

## The Download json Schema
You can also use [AQL](https://www.jfrog.com/confluence/display/RTF/Artifactory+Query+Language) instead of the wildcards pattern:
```
{
  "files": [
    {
      "pattern" OR "aql": "[Mandatory]",
      "target": "[Mandatory]",
      "recursive": "[Optional]",
      "flat" : "[Optional]",
      "props": "[Optional]"
    }
  ]
}
```
## The Upload json Schema
```
{
  "files": [
    {
      "pattern": "[Mandatory]",
      "target": "[Mandatory]",
      "recursive": "[Optional]",
      "flat" : "[Optional]",
      "props": "[Optional]"
    }
  ]
}
```
## Examples
We included a few examples to help you get started using the Artifactory DSL in your Pipeline scripts.

The *props-example* downloads and uploads files to Artifactory with properties. 
It also uses a placeholder when uploading. 