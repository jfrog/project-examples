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
                    buildName: 'MK',
                    buildNumber: '48',
                    serverId: SERVER_ID, // Obtain an Artifactory server instance, defined in Jenkins --> Manage:
                    specPath: 'jenkins-examples/pipeline-examples/resources/props-upload.json'
                )
            }
        }

        stage ('Download') {
            steps {
                rtDownload (
                    buildName: 'MK',
                    buildNumber: '48',
                    serverId: SERVER_ID,
                    specPath: 'jenkins-examples/pipeline-examples/resources/props-download.json'
                )
            }
        }

        stage ('Publish build info') {
            steps {
                rtPublishBuildInfo (
                    buildName: 'MK',
                    buildNumber: '48',
                    serverId: SERVER_ID
                )
            }
        }

        stage ('Promotion') {
            steps {
                rtPromote (
                    //Mandatory parameter
                    serverId: SERVER_ID,
                    targetRepo: 'libs-release-local',

                    //Optional parameters
                    buildName: 'MK',
                    buildNumber: '48',
                    comment: 'this is the promotion comment',
                    sourceRepo: 'libs-snapshot-local',
                    status: 'Released',
                    includeDependencies: true,
                    failFast: true,
                    copy: true
                )
            }
        }
    }
}
