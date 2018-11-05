pipeline {
    agent any

    stages {
        stage ('Clone') {
            steps {
                git branch: 'master', url: "https://github.com/jfrog/project-examples.git"
            }
        }

        stage ('Upload') {
            steps {
                rtUpload (
                    serverId: SERVER_ID, // Obtain an Artifactory server instance, defined in Jenkins --> Manage:
                    specPath: 'jenkins-examples/pipeline-examples/resources/props-upload.json'
                )
            }
        }

        stage ('Download') {
            steps {
                rtDownload (
                    serverId: SERVER_ID,
                    specPath: 'jenkins-examples/pipeline-examples/resources/props-download.json'
                )
            }
        }

        stage ('Publish build info') {
            steps {
                rtPublishBuildInfo (
                    serverId: SERVER_ID
                )
            }
        }
    }
}
