# Jenkins Pipeline - Working With Artifactory

## Introduction
The Pipeline Jenkins Plugin allows you to create a script that defines your build steps. 
For those not familiar with Jenkins Pipeline, check out the [Pipeline Tutorial](https://github.com/jenkinsci/pipeline-plugin/blob/master/TUTORIAL.md) or the [Getting Started With Pipeline](https://jenkins.io/doc/pipeline/) documentation.

The Artifactory Pipeline DSL documentation is available [here](https://wiki.jenkins-ci.org/display/JENKINS/Working+With+the+Pipeline+Jenkins+Plugin).

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
