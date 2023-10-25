# Gradle Version Catalog Example

## Introduction

This example demonstrates the process of sharing a [TOML version catalog](https://docs.gradle.org/current/userguide/platforms.html#sec:sharing-catalogs) file within JFrog Artifactory and how to make effective use of it. We'll explore two distinct Gradle projects: 'version-catalog-producer' and 'version-catalog-consumer'. In the 'version-catalog-producer' project, we'll create and upload a TOML file that includes commons-lang3 version 3.9. In the 'version-catalog-consumer' project, we'll use this TOML file to resolve version 3.9 of commons-lang3.

### Prerequisites

1. Start by [downloading and installing](https://jfrog.com/getcli) the latest version of JFrog CLI.
2. Set up your JFrog instance by using the [jf c add](https://docs.jfrog-applications.jfrog.io/jfrog-applications/jfrog-cli/configurations/jfrog-platform-configuration) command.

### Version Catalog Producer

The Version Catalog producer generates a `gradle-version-catalog-1.0.0.toml` file and uploads it to JFrog Artifactory as an artifact.

#### How to Proceed

1. Navigate to the 'version-catalog-producer' directory.
2. Execute the [jf gradle-config](https://docs.jfrog-applications.jfrog.io/jfrog-applications/jfrog-cli/cli-for-jfrog-artifactory#setting-gradle-repositories) command to generate the Gradle configuration:

```sh
jf gradle-config --repo-resolve <GRADLE_RESOLVE_REPO> --repo-deploy <GRADLE_DEPLOY_REPO> --use-wrapper
```

3. Run the [jf gradle clean artifactoryPublish](https://docs.jfrog-applications.jfrog.io/jfrog-applications/jfrog-cli/cli-for-jfrog-artifactory#running-gradle) command to create and upload the `gradle-version-catalog-1.0.0.toml` to JFrog Artifactory:

```sh
jf gradle clean artifactoryPublish
```

### Version Catalog Consumer

The Version Catalog consumer retrieves `gradle-version-catalog-1.0.0.toml` artifact from JFrog Artifactory and use it to obtain dependencies.

#### How to Proceed

1. Go to the 'version-catalog-consumer' directory.
2. Execute the [jf gradle-config](https://docs.jfrog-applications.jfrog.io/jfrog-applications/jfrog-cli/cli-for-jfrog-artifactory#setting-gradle-repositories) command to set up Gradle configuration:

```sh
jf gradle-config --repo-resolve <GRADLE_RESOLVE_REPO> --repo-deploy <GRADLE_DEPLOY_REPO> --use-wrapper
```

3. Execute the [jf gradle clean artifactoryPublish](https://docs.jfrog-applications.jfrog.io/jfrog-applications/jfrog-cli/cli-for-jfrog-artifactory#running-gradle) command to download and utilize the `gradle-version-catalog-1.0.0.toml` from JFrog Artifactory and deploy the application to JFrog Artifactory:

```sh
jf gradle clean artifactoryPublish
```