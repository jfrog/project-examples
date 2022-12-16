pipeline {
  agent any
  stages {
    stage('') {
      steps {
        script {
          node {
            def server
            def rtMaven = Artifactory.newMavenBuild()
            def buildInfo

            stage ('Clone') {
              git url: 'https://github.com/jonathanweh/project-examples.git'
            }

            stage ('Artifactory configuration') {
              // Obtain an Artifactory server instance, defined in Jenkins --> Manage Jenkins --> Configure System:
              server = Artifactory.server 'myart'

              // Tool name from Jenkins configuration
              rtMaven.tool = 'maven'
              rtMaven.deployer releaseRepo: 'libs-release-local, snapshotRepo: 'libs-snapshot-local, server: server
              rtMaven.resolver releaseRepo: 'libs-release, snapshotRepo: 'libs-snapshot, server: server
              buildInfo = Artifactory.newBuildInfo()
            }

            stage ('Exec Maven') {
              rtMaven.run pom: 'maven-examples/maven-example/pom.xml', goals: 'clean install', buildInfo: buildInfo
            }

            stage ('Publish build info') {
              server.publishBuildInfo buildInfo
            }
          }
          Footer
        }

      }
    }

  }
}