// Mandatory - Set the following Git repository in the Source control management section:
// https://github.com/jfrog/project-examples.git
pipelineJob('job-dsl-artifactory-pipeline-example') {
    parameters {
        stringParam('SERVER_ID', SERVER_ID, 'Enter Artifactory server ID')
    }

    definition {
        cps {
            script(readFileFromWorkspace('jenkins-examples/pipeline-examples/scripted-examples/declarative-example/Jenkinsfile'))
            sandbox()
        }
    }
}