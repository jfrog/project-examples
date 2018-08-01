# Integrating JFrog Artifactory with Drone CI using JFrog CLI

## Store artifacts and build information to Artifactory

### Follow this step-by-step workflow to Integrate Drone with Artifactory.
This sample project resolves dependencies from Artifactory and deploys the build artifacts to Artifactory.

#### Prerequisite
Artifactory Pro / Artifactory AOL  

#### Step 1:
In your project, configure the following Artifactory credentials, under CI/CD Settings > Secret variables: ARTIFACTORY_URL, ARTIFACTORY_USER, ARTIFACTORY_PASSWORD, and REPOSITORY_KEY.
![screenshot](img/Screenshot1.png)

#### Step 2:
Choose one of the below projects and push it into your Github repository, make sure that the project structure is maintained and also that the .drone file is at the root of the repository 

#### Step 3:
Activate your repository in the  drone server and trigger a build


#### Step 4:
View the published artifacts and build information in Artifactory.
![screenshot](img/Screenshot2.png)


##### Projects for different package types:

* [Maven](drone-maven)
* [Gradle](drone-gradle)
* [Npm](drone-npm-artifactory)
* [GO](drone-go)


