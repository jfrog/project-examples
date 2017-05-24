## Maven Artifactory Plugin Examples

### Overview
This repo contains all of the maven examples.

#### maven-example
This is an empty example template, that you can use to experiment with maven.
You would use this to see how Jenkins and Team City interact with artifactory
when building a simple java project.


#### maven-example-bintray-info
This example adds a maven deployment descriptor.
This attaches a bintray-info.json to the package that is deployed.
There is an artifactory rest api that requires this json file to be attached to an artifact
to push the artifact to bintray.



#### artifactory-maven-plugin-example
This example is using the Maven Artifactory Plugin for artifacts and build-info deployment to 
Artifactory. 
The Plugin documentation is available [here](https://www.jfrog.com/confluence/display/RTF/Maven+Artifactory+Plugin).

##### Deploying to your artifactory instance
The way this pom is configured it assumes a few things:
1. The artifactory instance is running on localhost at port 8081
1. The username is deployer
1. The password is set as an env variable ARTIFACTORY_PASSWORD

To deploy this example to an artifactory SAAS service named example (API_KEY should be the api key from your ci user) use:
ARTIFACTORY_USER=my-ci-user ARTIFACTORY_PASSWORD=API_KEY ARTIFACTORY_CONTEXT_URL=https://example.jfrog.io/example mvn deploy 
