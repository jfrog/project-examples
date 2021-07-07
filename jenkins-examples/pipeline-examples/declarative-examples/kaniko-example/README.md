# Kaniko Example

## Prerequisites

* Make sure to have the following job parameters configured:
    * `SERVER_ID` - JFrog instance ID, defined in Jenkins --> Manage Jenkins --> Configure System
    * `CREDENTIALS` - Credentials parameter type with username and password
    * `ARTIFACTORY_DOCKER_REPO` - Artifactory virtual or remote docker repository (i.e. docker-virtual)
    * `ARTIFACTORY_DOCKER_REGISTRY` - Artifactory docker registry (i.e. acme-docker-virtual.jfrog.io)