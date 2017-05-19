## Maven Artifactory Plugin Examples

### Overview
The Maven Artifactory Plugin allows you to deploy your build artifacts and build information to Artifactory and also to resolve
your build dependencies from Artifactory.
The Plugin documentation is available [here](https://www.jfrog.com/confluence/display/RTF/Maven+Artifactory+Plugin).

### Deploying to your artifactory instance
The way this pom is configured it assumes a few things:
1. The artifactory instance is running on localhost at port 8081
1. The username is deployer
1. The password is set as an env variable ARTIFACTORY_PASSWORD

To deploy this example to an artifactory SAAS service named example (API_KEY should be the api key from your ci user) use:
ARTIFACTORY_USER=my-ci-user ARTIFACTORY_PASSWORD=API_KEY ARTIFACTORY_CONTEXT_URL=https://example.jfrog.io/example mvn deploy 
