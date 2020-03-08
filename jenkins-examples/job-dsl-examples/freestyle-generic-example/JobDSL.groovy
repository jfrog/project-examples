def upSpec = """{
    "files": [
            {
                "pattern": "jenkins-examples/pipeline-examples/resources/ArtifactoryPipeline.zip",
                "target": "libs-snapshot-local"
            }
    ]
}"""

job("job-dsl-artifactory-freestyle-generic-example") {
    scm {
        git("https://github.com/jfrog/project-examples.git", "master")
    }

    configure { node ->
        node / 'buildWrappers' << 'org.jfrog.hudson.generic.ArtifactoryGenericConfigurator' {
            // === Deployer ===
            deployerDetails {
                artifactoryName SERVER_ID
            }
            // Optional - Override deployer credentials
            //deployerCredentialsConfig {
            //    credentialsId ARTIFACTORY_CREDENTIALS
            //}

            // === Resolver ===
            resolverDetails {
                artifactoryName SERVER_ID
            }
            // Optional - Override resolver credentials
            //resolverCredentialsConfig {
            //    credentialsId ARTIFACTORY_CREDENTIALS
            //}

            // === Specify which files to upload ===
            uploadSpec {
                spec upSpec // Spec from a string source
            }

            // === Specify which files to download ===
            downloadSpec {
                filePath 'jenkins-examples/pipeline-examples/resources/props-download.json' // Spec from a file source
            }

            deployBuildInfo true // Check if you wish to publish build information to Artifactory.

            // === Include environment variables ===
            includeEnvVars true // Check if you wish to include all environment variables accessible by the builds process.
            envVarsPatterns {
                // Comma or space-separated list of environment variables that will be included as part of the published build info.
                // Environment variables may contain the * and the ? wildcards. Include patterns are applied before any exclude patterns.
                includePatterns ''
                excludePatterns '*password*,*secret*,*key*'
            }

            // === Discard old builds from Artifactory ===
            discardOldBuilds false // Automatically remove old builds stored in Artifactory according to Jenkins's configured policy for discarding old builds.
            discardBuildArtifacts false // Automatically remove build artifacts stored in Artifactory according to Jenkins's configured policy for discarding old builds.
            asyncBuildRetention false // Check for asynchronous build retention

            // === Override build name ===
            overrideBuildName false // Check if you wish to override Artifactory default build name.
            customBuildName 'CUSTOM_BUILD_NAME' // Sets the new Artifactory build name.
        }
    }
}