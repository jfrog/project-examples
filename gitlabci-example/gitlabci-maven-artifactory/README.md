# Artifactory Integration with GitLab CI using JFrog CLI

## Store build information and build artifacts to JFrog Artifactory

### Steps to Integrate GitLab CI with Artifactory.

#### Step 1:

Configure the following Secret Variables in CI/CD Settings of your Maven project: ARTIFACTORY_URL, ARTIFACTORY_USER, ARTIFACTORY_PASS, MAVEN_REPO_KEY.

![screenshot](img/Screen_Shot1.png)

#### Step 2:

Place a .gitlab-ci.yml in the root of your project, following this example.

#### Step 4:

You should be able to see published artifacts and build info in artifactory.

![screenshot](img/Screen_Shot2.png)
