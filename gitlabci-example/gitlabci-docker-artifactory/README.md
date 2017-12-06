# Artifactory Integration with GitLab CI
## Store build information and build artifacts to JFrog Artifactory

`To make this integration work you will need to have running Artifactory-pro/Artifactory SAAS.`

### Steps to build docker images using Circle CI and push it to Artifactory.

#### Step 1:

Configure the following Secret Variables in CI/CD Settings of your Maven project: ARTIFACTORY_URL, ARTIFACTORY_USER, ARTIFACTORY_PASS, ARTIFACTORY_DOCKER_REPOSITORY.
![screenshot](img/Screen_Shot1.png)

##### Step 2:

Place a .gitlab-ci.yml in the root of your project, following this example.

##### Step 3:

You should be able to see published Docker image in Artifactory.
![screenshot](img/Screen_Shot2.png)
