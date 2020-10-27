# Declarative pipeline examples:

The examples here are meant to help you get started working with Artifactory in your Jenkins pipeline scripts.
To set up Jenkins to use the example, read [this](https://github.com/jfrog/project-examples/tree/master/jenkins-examples/pipeline-examples) page.

* The [aql-example](aql-example) uses a Download Spec which includes [AQL](https://www.jfrog.com/confluence/display/RTF/Artifactory+Query+Language) instead of a wildcard pattern.
* The [build-retention-example](build-retention-example) demonstrates triggering build retention in Artifactory.
* The [build-scan-example](build-scan-example) demonstrates how to scan published builds with JFrog Xray.
* The [build-trigger-example](build-trigger-example) demonstrates how to trigger a build when files are added or modified in a specific Artifactory path.
* The [conan-example](conan-example) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a Conan build.
* The [docker-push-example](docker-push-example) demonstrates how to push a docker image to Artifactory.
* The [docker-pull-example](docker-pull-example) demonstrates how to pull a docker image from Artifactory.
* The [dotnet-example](dotnet-example) resolves dependencies and publishes build-info to Artifactory for a .NET build.
* The [exclude-patterns-download-example](exclude-patterns-download-example) demonstrates how to exclude certain files while downloading.
* The [exclude-patterns-upload-example](exclude-patterns-upload-example) demonstrates how to exclude certain files while uploading.
* The [go-example](go-example) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a Go build.
* The [gradle-example-ci-server](gradle-example-ci-server) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a Gradle build. Unlike the [gradle-example](gradle-example), this examples assumes that the Gradle Artifactory Plugin in not applied to the Gradle build script.
* The [gradle-example](gradle-example) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a Gradle build. Unlike the [gradle-example-ci-server](gradle-example-ci-server), this examples assumes that the Gradle Artifactory Plugin in already applied in the Gradle build script.
* The [interactive-promotion-example](interactive-promotion-example) demonstrates how to promote a build in Artifactory after the build finished.
* The [issues-collection-example](issues-collection-example) demonstrates how to collect the list of tracked project issues and add them to the build-info.
* The [jfrog-pipelines-example](jfrog-pipelines-example) demonstrates how to collect output resources when using JFrog Pipelines integration.
* The [maven-example](maven-example) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a Maven build.
* The [npm-container-example](npm-container-example) demonstrates how to run npm in a Docker container. Use this example only in **Multibranch Pipeline** or a **Pipeline from SCM**, as instructed [here](https://jenkins.io/doc/book/pipeline/syntax/#agent) under **dockerfile**.
* The [npm-example](npm-example) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a npm build.
* The [nuget-example](nuget-example) resolves dependencies and publishes build-info to Artifactory for a NuGet build.
* The [pip-examples](pip-examples) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a pip build.
* The [promotion-example](promotion-example) demonstrates how to promote a build in Artifactory.
* The [props-example](props-example) demonstrates setting/deleting properties on artifacts in Artifactory. Also downloads and uploads files to Artifactory with properties, while using a placeholder when downloading.
* The [props-single-file-example](props-single-file-example) is the same as the [props-example](props-example), but has the specs embedded inside the Groovy script.
