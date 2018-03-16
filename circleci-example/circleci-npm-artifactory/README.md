# circleci-npm-artifactory

[![CircleCI](https://circleci.com/gh/jainishshah17/circleci-npm-artifactory.svg?style=svg)](https://circleci.com/gh/jainishshah17/circleci-npm-artifactory)
## Store build information and build artifacts to JFrog Artifactory
## Artifactory Integration with CircleCI

`To make this integration  work you will need to have running Artifactory-pro/Artifactory SAAS.`

#### NPM Example
This is a sample project that resolve a dependency from Artifactory and deploys the build artifacts to Artifactory.

Step 1:

copy ```circle.yml``` file to your project.

Step 2: 

Enable your project in CircleCI .

![screenshot](img/Screen_Shot1.png)

Step 3:

add Environment Variables ARTIFACTORY_URL, ARTIFACTORY_USER and ARTIFACTORY_PASSWORD in build settings of travis-ci.

![screenshot](img/Screen_Shot2.png)

Step 4:

Trigger build.

Step 5: 

You should be able to see published artifacts in artifactory.

![screenshot](img/Screen_Shot3.png)
