# Integrating JFrog Artifactory with Drone CI using JFrog CLI
## Store artifacts and build information to Artifactory
### Follow this step-by-step workflow to Integrate Drone with Artifactory.
This sample project resolves dependencies from Artifactory and deploys the build artifacts to Artifactory.
#### Prerequisite
Artifactory Pro / Artifactory AOL  

#### Step 1:
In the Maven project, configure the following Artifactory credentials, under CI/CD Settings > Secret variables: ARTIFACTORY_URL, ARTIFACTORY_USER, ARTIFACTORY_PASSWORD, and MAVEN_REPO_KEY.
![screenshot](img/ScreenShot1.png)

#### Step 2:
Place a .drone.yml file in the root of your project, as used in this sample project.

#### Step 3:
View the published artifacts and build information in Artifactory.
![screenshot](img/ScreenShot2.png)
