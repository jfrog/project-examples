# Declarative pipeline examples:

The examples here are meant to help you get started working with Artifactory in your Jenkins pipeline scripts.
To set up Jenkins to use the example, read [this](https://github.com/jfrog/project-examples/tree/master/jenkins-examples/pipeline-examples) page.

* The [aql-example](aql-example) uses a Download Spec which includes [AQL](https://www.jfrog.com/confluence/display/RTF/Artifactory+Query+Language) instead of a wildcard pattern.
* The [build-retention-example](build-retention-example) demonstrates triggering build retention in Artifactory.
* The [build-scan-example](build-scan-example) demonstrates how to scan published builds with JFrog Xray.
* The [docker-push-example](docker-push-example) demonstrates how to push a docker image to Artifactory.
* The [exclude-patterns-download-example](exclude-patterns-download-example) demonstrates how to exclude certain files while downloading.
* The [exclude-patterns-upload-example](exclude-patterns-upload-example) demonstrates how to exclude certain files while uploading.
* The [go-example](go-example) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a Go build.
* The [gradle-example-ci-server](gradle-example-ci-server) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a Gradle build. Unlike the [gradle-example](gradle-example), this examples assumes that the Gradle Artifactory Plugin in not applied to the Gradle build script.
* The [gradle-example](gradle-example) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a Gradle build. Unlike the [gradle-example-ci-server](gradle-example-ci-server), this examples assumes that the Gradle Artifactory Plugin in already applied in the Gradle build script.
* The [interactive-promotion-example](interactive-promotion-example) demonstrates how to promote a build in Artifactory after the build finished.
* The [issues-collection-example](issues-collection-example) demonstrates how to collect the list of tracked project issues and add them to the build-info.
* The [maven-example](maven-example) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a Maven build.
* The [npm-container-example](npm-container-example) demonstrates how to run npm in a Docker container.
* The [npm-example](npm-example) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a npm build.
* The [promotion-example](promotion-example) demonstrates how to promote a build in Artifactory.
* The [props-example](props-example) demonstrates setting/deleting properties on artifacts in Artifactory. Also downloads and uploads files to Artifactory with properties, while using a placeholder when downloading.
* The [props-single-file-example](props-single-file-example) is the same as the [props-example](props-example), but has the specs embedded inside the Groovy script.
