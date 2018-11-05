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
                    spec: """{
                            "files": [
                                    {
                                        "pattern": "jenkins-examples/pipeline-examples/resources/ArtifactoryPipeline.zip",
                                        "target": "libs-snapshot-local",
                                        "props": "p1=v1;p2=v2"
                                    },
                                    {
                                        "pattern": "jenkins-examples/pipeline-examples/resources/ArtifactoryPipelineNoProps.zip",
                                        "target": "libs-snapshot-local"
                                    }
                                ]
                            }"""
                )
            }
        }

        stage ('Download') {
            steps {
                rtDownload (
                    serverId: SERVER_ID,
                    spec: """{
                            "files": [
                                    {
                                        "pattern": "libs-snapshot-local/*(Pipeline).zip",
                                        "target": "Bazinga/{1}/",
                                        "props": "p1=v1;p2=v2"
                                    }
                                ]
                            }"""
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
