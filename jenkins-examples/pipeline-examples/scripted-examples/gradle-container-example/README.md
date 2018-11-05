# Gradle Container Example
## Prerequisites
* Make sure that Java and Gradle installed inside your Docker container.
* In order to use Artifactory integration with Docker containers, Gradle installation path should be set by one of the following:
    * Option 1: In Jenkins Manage, configure a new Gradle tool.
        * Use `GRADLE_HOME` as configured in the container. In our example use `/opt/gradle`.
        * Uncheck `Install automatically`.
        * Add the configured Gradle tool name to the pipeline script:
            * rtGradle.tool = GRADLE_TOOL
    * Option 2: Set `GRADLE_HOME` environment variable as configured in the container.
        *     withEnv(['GRADLE_HOME=/opt/gradle']) {
                rtGradle.run rootDir: "gradle-examples/gradle-example/", buildFile: 'build.gradle', tasks: 'clean artifactoryPublish', buildInfo: buildInfo
              }
