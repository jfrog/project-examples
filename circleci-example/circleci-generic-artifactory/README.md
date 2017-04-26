# Artifactory Integration with Circle CI using Artifactory CLI
### Store build information and build artifacts to JFrog Artifactory
## Build Status

[![CircleCI](https://circleci.com/gh/jainishshah17/circleci-generic-artifactory.svg?style=svg)](https://circleci.com/gh/jainishshah17/circleci-generic-artifactory)

`To make this integration work you will need to have running Artifactory-pro/Artifactory SAAS.`

## Steps to Integrate Circle CI with Artifactory.

Step 1:

copy `circle.yml` file to your project.

Step 2:

Enable your project in CircleCI.

![screenshot](img/Screen_Shot1.png)

Step 3:

add Environment Variables ARTIFACTORY_URL, ARTIFACTORY_USERNAME and ARTIFACTORY_PASSWORD in build settings of CircleCI.

![screenshot](img/Screen_Shot2.png)

Step 5:

You should be able to see published artifacts in artifactory.

![screenshot](img/Screen_Shot3.png)

Step 6: 

Check build info in build section of Artifactory.

![screenshot](img/Screen_Shot4.png)
