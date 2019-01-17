# Jenkins Pipeline - Working With Artifactory

## Introduction
Pipeline jobs in Jenkins allow creating a script which defines your build steps. 
For those not familiar with Jenkins Pipelines, check out the [Pipeline Tutorial](https://github.com/jenkinsci/pipeline-plugin/blob/master/TUTORIAL.md) or the [Getting Started With Pipeline](https://jenkins.io/doc/book/pipeline/) documentation.

Read the [Working With Pipeline Jobs in Jenkins](https://www.jfrog.com/confluence/display/RTF/Working+With+Pipeline+Jobs+in+Jenkins) documentation for information on working with JFrog Artifactory using Jenkins pipeline jobs.

## Examples
The examples in this repository are meant to help you get started using the Artifactory Pipeline APIs in your pipeline scripts.

Follow the below steps to run the examples.

### Step 1 - Configure Artifactory Server for the Examples
All examples use an Artifactory server defined in Manage Jenkins, Configure System.
The server is referenced in all examples using the SERVER_ID variable.
To have the SERVER_ID variable reference your Artifactory server, follow these steps:
* Find or configure an Artifactory server in Jenkins through Manage Jenkins, Configure System. You'll need its Server ID.
* In the Jenkins job configuration, check the *This project is parameterized* option.
* Click *Add Parameter* and then select *String Parameter*.
* Set the *Name* as *SERVER_ID* and the *Default Value* as the Artifactory Server ID configured in *Manage Jenkins*.

### Step 2 - Configure the Jenkins Job

Configure the Jenkins using one of the following methods.
#### Method 1:
In the job configuration, set *Definition* to *Pipeline script*,
and then copy the content of the example's Jenkinsfile into the *Script* text-area.
#### Method 2:
In the job configuration:
* Set *Definition* to *Pipeline script from SCM*.
* Set *SCM* to *Git*.
* Set *Repository URL* to *https://github.com/jfrog/project-examples.git*
* Set *Script Path* to the relative path to the example's Jenkinsfile. For example, to run the [maven-example](declarative-examples/maven-example/Jenkinsfile) set *Script Path* to *jenkins-examples/pipeline-examples/declarative-examples/maven-example/Jenkinsfile* 

### Available Examples
This repository includes [Declarative pipeline examples](declarative-examples) and [Scripted pipeline examples](scripted-examples)

Learn more about [working with pipeline jobs in Jenkins](https://www.jfrog.com/confluence/display/RTF/Working+With+Pipeline+Jobs+in+Jenkins) and the benefits of [Artifactory’s integration with Jenkins CI](https://jfrog.com/integration/jenkins-ci/).