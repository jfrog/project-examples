# Maven Artifactory Plugin

## Overview

This example is using the Maven Artifactory Plugin for artifacts and build-info deployment to Artifactory. 

## Running this example

To run this example, please do the following:
* Edit the pom.xml file and set the value of the *contextUrl* with your Artifactory URL, as well as the other Artifactory properties.
For more configuration information see the [Maven Artifactory Plugin documentation](https://www.jfrog.com/confluence/display/RTF/Maven+Artifactory+Plugin).
* CD to the project directory and run *mvn deploy*.

You can define the Artifactory details inside the pom file using properties. These properties can be sent to maven as system properties: 
```console
mvn deploy -Dusername=admin -Dpassword=password -Dbuildnumber=1
```

Alternatively they can be defined in the settings xml as described [here](https://maven.apache.org/examples/injecting-properties-via-settings.html).

```console
mvn deploy -Dbuildnumber=1 -s settings.xml
```

This would deploy the produced artifacts to the configured Artifactory server:

```console
 [INFO] Artifactory Build Info Recorder: Saving Build Info to 'C:\dev\project-examples\artifactory-maven-plugin-example\target\build-info.json'
 [INFO] Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi3/2.17-SNAPSHOT/multi3-2.17-SNAPSHOT.war
 [INFO] Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi3/2.17-SNAPSHOT/multi3-2.17-SNAPSHOT.pom
 [INFO] Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi/2.17-SNAPSHOT/multi-2.17-SNAPSHOT.pom
 [INFO] Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi1/2.17-SNAPSHOT/multi1-2.17-SNAPSHOT.jar
 [INFO] Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi1/2.17-SNAPSHOT/multi1-2.17-SNAPSHOT.pom
 [INFO] Artifactory Build Info Recorder: Skipping the deployment of 'org/jfrog/test/multi1/2.17-SNAPSHOT/multi1-2.17-SNAPSHOT-tests.jar' due to the defined include-exclude patterns.
 [INFO] Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi1/2.17-SNAPSHOT/multi1-2.17-SNAPSHOT-sources.jar
 [INFO] Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi2/2.17-SNAPSHOT/multi2-2.17-SNAPSHOT.jar
 [INFO] Deploying artifact: http://localhost:8081/artifactory/libs-snapshot-local/org/jfrog/test/multi2/2.17-SNAPSHOT/multi2-2.17-SNAPSHOT.pom
 [INFO] Artifactory Build Info Recorder: Deploying build info ...
 [INFO] Deploying build descriptor to: http://localhost:8081/artifactory/api/build
 [INFO] Build successfully deployed. Browse it in Artifactory under http://localhost:8081/artifactory/webapp/builds/plugin-demo/1
```

## Sending parameters to the pom file

You can send parameters as system properties, and then use them inside the pom file, the same way the *username* and *password*
properties are sent as shown above.

## Plugin documentation

The full plugin documentation is available [here](https://www.jfrog.com/confluence/display/RTF/Maven+Artifactory+Plugin).

<br><br>

For more information about managing your maven packages with an [Artifactory Maven repository](https://jfrog.com/integration/maven-repository/) visit [Artifactory's documentation page](https://www.jfrog.com/confluence/display/RTF/Maven+Repository).
