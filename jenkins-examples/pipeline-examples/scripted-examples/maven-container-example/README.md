# Maven Container Example
## Prerequisites
* Make sure that Java and Maven installed inside your Docker container.
* In order to use Artifactory integration with Docker containers, Maven and Java installations paths should be set by one of the following:
    * Option 1: In Jenkins Manage, configure a new Maven tool.
        * Use `MAVEN_HOME` as configured in the container. In our example use `/usr/share/maven`.
        * Uncheck `Install automatically`.
        * Add the configured Maven tool name to the pipeline script:
            * rtMaven.tool = CONTAINER_MAVEN_TOOL
        * Add `JAVA_HOME` environment variable to the pipeline script.
            *       withEnv(['JAVA_HOME=/usr/local/openjdk-8']) { // Java home of the container
                        rtMaven.run pom: 'maven-example/pom.xml', goals: 'clean install', buildInfo: buildInfo
                    }
    * Option 2: Set `JAVA_HOME` and `MAVEN_HOME` environment variables as configured in the container.
        *     withEnv(['JAVA_HOME=/usr/local/openjdk-8', 'MAVEN_HOME=/usr/share/maven']) {
                rtMaven.run pom: 'maven-example/pom.xml', goals: 'clean install', buildInfo: buildInfo
              }