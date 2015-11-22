### Artifactory Maven Plugin

## Overview

Artifactory Maven plugin example is using the Artifactory Maven plugin for deployment the artifacts and using the Build-Info ability with Artifactory. You can run this plugin (after changing the configuration to match your Artifactory server) just by runnung:

```console
> mvn deploy

```

This would deploy the produced artifacts to the configured Artifactory:

```console
 Artifactory Build Info Recorder: Saving Build Info to '**\artifactory-maven-plugin-example\target\build-info.json'
 Artifactory Build Info Recorder: Deploying artifacts to http://localhost:8081/artifactory/libs-release-local
 Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi/2.17-SNAPSHOT/multi-2.17-SNAPSHOT.pom
 Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi1/2.17-SNAPSHOT/multi1-2.17-SNAPSHOT.jar
 Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi1/2.17-SNAPSHOT/multi1-2.17-SNAPSHOT.pom
 Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi1/2.17-SNAPSHOT/multi1-2.17-SNAPSHOT-sources.jar
 Artifactory Build Info Recorder: Skipping the deployment of 'org/jfrog/test/multi1/2.17-SNAPSHOT/multi1-2.17-SNAPSHOT-tests.jar' due to the defined include-exclude patterns.
 Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi2/2.17-SNAPSHOT/multi2-2.17-SNAPSHOT.jar
 Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi2/2.17-SNAPSHOT/multi2-2.17-SNAPSHOT.pom
 Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi3/2.17-SNAPSHOT/multi3-2.17-SNAPSHOT.war
 Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi3/2.17-SNAPSHOT/multi3-2.17-SNAPSHOT.pom
 Artifactory Build Info Recorder: Deploying build info ...
 Deploying build info to: http://localhost:8081/artifactory/api/build

```


## Using -D parameter for different values like username and password

The username and password can be passed using the -D parameter:

```console
> mvn deploy -Dusername=admin -Dpassword=MyPassword

```

This would set the username to "admin" and the password to "MyPassword"

