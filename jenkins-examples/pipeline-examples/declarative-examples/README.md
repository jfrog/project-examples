# Declarative pipeline examples:

The examples here are meant to help you get started working with Artifactory in your Jenkins pipeline scripts.
To set up Jenkins to use the example, read [this](https://github.com/jfrog/project-examples/tree/master/jenkins-examples/pipeline-examples) page.

* The [props-example](props-example) downloads and uploads files to Artifactory with properties. The props-example also uses a placeholder when uploading.
* The [props-single-file-example](props-single-file-example) is the same as the [props-example](props-example), but has the specs embedded inside the Groovy script.
* The [promotion-example](promotion-example) demonstrates how to promote a build in Artifactory.
* The [interactive-promotion-example](interactive-promotion-example) demonstrates how to promote a build in Artifactory after the build finished.
* The [build-retention-example](build-retention-example) demonstrates triggering build retention in Artifactory.
* The [aql-example](aql-example) uses a Download Spec which includes [AQL](https://www.jfrog.com/confluence/display/RTF/Artifactory+Query+Language) instead of a wildcard pattern.
* The [maven-example](maven-example) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a Maven build.
* The [gradle-example-ci-server](gradle-example-ci-server) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a Gradle build. Unlike the [gradle-example](gradle-example), this examples assumes that the Gradle Artifactory Plugin in not applied to the Gradle build script.
* The [gradle-example](gradle-example) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a Gradle build. Unlike the [gradle-example-ci-server](gradle-example-ci-server), this examples assumes that the Gradle Artifactory Plugin in already applied in the Gradle build script.
* The [exclude-patterns-download-example](exclude-patterns-download-example) demonstrates how to exclude certain files while downloading.
* The [exclude-patterns-upload-example](exclude-patterns-upload-example) demonstrates how to exclude certain files while uploading.
* The [npm-example](npm-example) resolves dependencies, deploys artifacts and publishes build-info to Artifactory for a npm build.
