node {
    def app
    def rtServer = Artifactory.newServer url: ART_SERVER_URL, credentialsId: CREDENTIAL_ID
    def buildInfo = Artifactory.newBuildInfo()

    stage('Clone repository') {
        /* Let's make sure we have the repository cloned to our workspace */
        git url: 'https://github.com/jainishshah17/node-version.git'
    }

    stage('Build image') {
        /* This builds the actual image; synonymous to
         * docker build on the command line */
        app = docker.build("node-version")
    }

    stage('Test image') {
        /* Ideally, we would run a test framework against our image.
         * For this example, we're using a Volkswagen-type approach ;-) */
        app.withRun('-p 3000:3000') {c ->
            sleep 10
            def stdout = sh(script: 'curl "http://localhost:3000/"', returnStdout: true)
            if (stdout.contains("Package Version is : ")) {
                println "*** Passed Test: " + stdout
            } else {
                println "*** Failed Test: " + stdout
                result = false
            }
        }
    }

    stage('Push image') {
        /* Finally, we'll push the image with two tags:
         * First, the incremental build number from Jenkins
         * Second, the 'latest' tag.
         * Pushing multiple tags is cheap, as all the layers are reused. */
        docker.withRegistry('https://'+ART_DOCKER_REGISTRY, CREDENTIAL_ID) {
            app.push("${env.BUILD_NUMBER}")
            app.push("latest")
        }
        /*
        Remove docker images
        */
        sh "docker rmi -f ${ART_DOCKER_REGISTRY}/node-version:${env.BUILD_NUMBER}"
        sh "docker rmi -f ${ART_DOCKER_REGISTRY}/node-version:latest"
        sh "docker rmi -f node-version"
        /*
        Capture and publish build information to Artifactory
        */
        buildInfo.env.collect()
        rtServer.publishBuildInfo buildInfo
    }
}