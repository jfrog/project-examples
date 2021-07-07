# Maven JIB Example

## Prerequisites

* Make sure to have the following job parameters configured:
    * `SERVER_ID` - JFrog instance ID, defined in Jenkins --> Manage Jenkins --> Configure System
    * `CREDENTIALS` - Credentials parameter type with username and password
    * `ARTIFACTORY_LOCAL_RELEASE_REPO` - Artifactory local releases repository for deployment (i.e. libs-release-local)
    * `ARTIFACTORY_LOCAL_SNAPSHOT_REPO` - Artifactory local snapshots repository for deployment (i.e.
      libs-snapshot-local)
    * `ARTIFACTORY_VIRTUAL_RELEASE_REPO` - Artifactory virtual releases repository for resolution (i.e. libs-release)
    * `ARTIFACTORY_VIRTUAL_SNAPSHOT_REPO` - Artifactory virtual snapshots repository for resolution (i.e libs-snapshot)
    * `ARTIFACTORY_DOCKER_REPO` - Artifactory virtual or remote docker repository (i.e. docker-virtual)
    * `ARTIFACTORY_DOCKER_REGISTRY` - Artifactory docker registry (i.e. acme-docker-virtual.jfrog.io)