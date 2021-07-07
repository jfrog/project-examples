node {
    def server
    def rtMaven = Artifactory.newMavenBuild()
    def buildInfo

    stage ('Clone') {
        git url: 'https://github.com/jfrog/project-examples.git'
    }

    stage ('Artifactory configuration') {
        // Obtain an Artifactory server instance, defined in Jenkins --> Manage Jenkins --> Configure System:
        server = Artifactory.server SERVER_ID

        // Tool name from Jenkins configuration
        rtMaven.tool = MAVEN_TOOL
        rtMaven.deployer releaseRepo: ARTIFACTORY_LOCAL_RELEASE_REPO, snapshotRepo: ARTIFACTORY_LOCAL_SNAPSHOT_REPO, server: server
        rtMaven.resolver releaseRepo: ARTIFACTORY_VIRTUAL_RELEASE_REPO, snapshotRepo: ARTIFACTORY_VIRTUAL_SNAPSHOT_REPO, server: server
        buildInfo = Artifactory.newBuildInfo()
    }

    stage ('Exec Maven') {
        rtMaven.run pom: 'maven-examples/maven-example/pom.xml', goals: 'clean install', buildInfo: buildInfo
    }

    stage ('Publish build info') {
        server.publishBuildInfo buildInfo
    }
}