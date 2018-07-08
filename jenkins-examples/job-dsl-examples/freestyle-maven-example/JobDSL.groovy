job("job-dsl-artifactory-freestyle-maven-example") {
    scm {
        git("https://github.com/JFrog/project-examples.git", "master")
    }

    configure { node ->
        // Configure the Maven builder
        node / 'builders' << 'org.jfrog.hudson.maven3.Maven3Builder' {
            goals 'clean install' // Specifies the goals to execute, such as "clean install" or "deploy"
            rootPom 'maven-example' // If your workspace has the top-level pom.xml in somewhere other than the module root directory, specify the path
            mavenName M3 // Tool name from Jenkins configuration
        }

        // Configure artifactory maven job
        node / 'buildWrappers' << 'org.jfrog.hudson.maven3.ArtifactoryMaven3Configurator' {
            // === Resolver ===
            enableResolveArtifacts true
            resolverDetails {
                artifactoryName SERVER_ID
                resolveReleaseRepository {
                    keyFromText 'libs-release'
                }
                resolveSnapshotRepository {
                    keyFromText 'libs-snapshot'
                }
            }
            // Optional - Override resolver credentials
            //resolverCredentialsConfig {
            //    credentialsId ARTIFACTORY_CREDENTIALS
            //}

            // === Deployer ===
            deployerDetails {
                artifactoryName SERVER_ID
                deployReleaseRepository {
                    keyFromText 'libs-release-local'
                }
                deploySnapshotRepository {
                    keyFromText 'libs-snapshot-local'
                }
            }
            // Optional - Override deployer credentials
            //deployerCredentialsConfig {
            //    credentialsId ARTIFACTORY_CREDENTIALS
            //}

            // === Override build name ===
            overrideBuildName false // Check if you wish to override Artifactory default build name.
            customBuildName '' // Sets the new Artifactory build name.

            // === Deploy artifacts to Artifactory ===
            deployArtifacts true
            artifactDeploymentPatterns {
                // Comma or space-separated list of Ant-style patterns of files that will be included in publishing. Include/Exclude patterns are applied on the published file path before any exclude patterns.
                includePatterns 'p1=v1;p2=v2'
                excludePatterns 'p3=v3;p4=v4'
            }

            // === Filter excluded artifacts from build Info ===
            filterExcludedArtifactsFromBuild true //  Add the excluded files to the excludedArtifacts list and remove them from the artifacts list in the build info.

            // === Capture and publish build info ===
            deployBuildInfo true // Check if you wish to publish build information to Artifactory.
            deploymentProperties 'buildStatus=RC;platforms=win386,win64,osx,debian' //  Semicolon-separated list of properties to attach to all deployed artifacts in addition to the default ones (build.name, build.number, vcs.revision, etc.). Property values can take environment variables.
            includeEnvVars true // Check if you wish to include all environment variables accessible by the builds process.
            envVarsPatterns {
                // Comma or space-separated list of environment variables that will be included as part of the published build info.
                // Environment variables may contain the * and the ? wildcards. Include patterns are applied before any exclude patterns.
                includePatterns ''
                excludePatterns '*password*,*secret*,*key*'
            }

            recordAllDependencies false // Check if you wish build information published to Artifactory to include implicit project dependencies

            // === Discard old builds from Artifactory (requires Artifactory Pro) ===
            discardOldBuilds false // Automatically remove old builds stored in Artifactory according to Jenkins's configured policy for discarding old builds.
            discardBuildArtifacts true // Automatically remove build artifacts stored in Artifactory according to Jenkins's configured policy for discarding old builds.
            asyncBuildRetention false // Check for asynchronous build retention.

            // === Jira integration ===
            enableIssueTrackerIntegration false // When the Jenkins JIRA plugin is enabled, synchronize information about JIRA issues to Artifactory and attach issue information to build artifacts. This allows you to quickly identify build artifacts that contain a fix for a specific issue.
            aggregateBuildIssues false //  When the Jenkins JIRA plugin is enabled, include all issues from previous builds up to the latest build status defined in "Aggregation Build Status" (not including it). Usually set to "Released" to include all issues of the current version up to the last released build.
            aggregationBuildStatus 'Released' // Define the latest build status to stop the aggregation at.
        }
    }
}
