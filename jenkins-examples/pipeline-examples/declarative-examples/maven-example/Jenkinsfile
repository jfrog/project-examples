pipeline {
    agent any
    stages {
        stage ('Clone') {
            steps {
                git branch: 'master', url: "https://github.com/jfrog/project-examples.git"
            }
        }

        stage ('Artifactory configuration') {
            steps {
                rtServer (
                    id: "ARTIFACTORY_SERVER",
                    url: SERVER_URL,
                    credentialsId: CREDENTIALS
                )

                rtMavenDeployer (
                    id: "MAVEN_DEPLOYER",
                    serverId: "ARTIFACTORY_SERVER",
                    releaseRepo: ARTIFACTORY_LOCAL_RELEASE_REPO,
                    snapshotRepo: ARTIFACTORY_LOCAL_SNAPSHOT_REPO
                )

                rtMavenResolver (
                    id: "MAVEN_RESOLVER",
                    serverId: "ARTIFACTORY_SERVER",
                    releaseRepo: ARTIFACTORY_VIRTUAL_RELEASE_REPO,
                    snapshotRepo: ARTIFACTORY_VIRTUAL_SNAPSHOT_REPO
                )
            }
        }

        stage ('Exec Maven') {
            steps {
                rtMavenRun (
                    tool: MAVEN_TOOL, // Tool name from Jenkins configuration
                    pom: 'maven-examples/maven-example/pom.xml',
                    goals: 'clean install',
                    deployerId: "MAVEN_DEPLOYER",
                    resolverId: "MAVEN_RESOLVER"
                )
            }
        }

        stage ('Publish build info') {
            steps {
                rtPublishBuildInfo (
                    serverId: "ARTIFACTORY_SERVER"
                )
            }
        }
    }
}