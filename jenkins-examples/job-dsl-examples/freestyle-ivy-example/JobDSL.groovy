job("job-dsl-artifactory-freestyle-ivy-example") {
    scm {
        git("https://github.com/jfrog/project-examples.git", "master")
    }

    configure { node ->
        // Configure the Gradle builder
        node / 'builders' << 'hudson.tasks.Ant' {
            targets 'publish-ci' // Specify a list of Ant targets to be invoked (separated by spaces), or leave it empty to invoke the default Ant target specified in the build script. Additionally, you can also use this field to specify other Ant options.
            buildFile 'ivy-example/build.xml' //  If your build requires a custom -buildfile, specify it here. By default Ant will use the build.xml in the root directory; this option can be used to use build files with a different name or in a subdirectory.
        }

        // Configure artifactory ivy job
        node / 'buildWrappers' << 'org.jfrog.hudson.ivy.ArtifactoryIvyFreeStyleConfigurator' {
            // === Deployer ===
            deployerDetails {
                artifactoryName SERVER_ID
                deployReleaseRepository {
                    keyFromText 'libs-release-local'
                }
            }
            // Optional - Override deployer credentials
            //deployerCredentialsConfig {
            //    credentialsId ARTIFACTORY_CREDENTIALS
            //}

            deployBuildInfo true // Check if you wish to publish build information to Artifactory.

            // === Include environment variables ===
            includeEnvVars true // Check if you wish to include all environment variables accessible by the builds process.
            envVarsPatterns {
                // Comma or space-separated list of environment variables that will be included as part of the published build info.
                // Environment variables may contain the * and the ? wildcards. Include patterns are applied before any exclude patterns.
                includePatterns ''
                excludePatterns '*password*,*secret*,*key*'
            }

            // === Discard old builds from Artifactory (requires Artifactory Pro) ===
            discardOldBuilds false // Automatically remove old builds stored in Artifactory according to Jenkins's configured policy for discarding old builds.
            discardBuildArtifacts true // Automatically remove build artifacts stored in Artifactory according to Jenkins's configured policy for discarding old builds.
            asyncBuildRetention false // Check for asynchronous build retention.

            // === Override build name ===
            overrideBuildName false // Check if you wish to override Artifactory default build name.
            customBuildName '' // Sets the new Artifactory build name.

            // === Publish artifacts to Artifactory ===
            deployArtifacts true // Check if you wish to publish produced build artifacts to Artifactory.
            useMavenPatterns true // Whether to use the default Maven patterns when publishing artifacts and Ivy descriptors, or to use custom patterns. Dots in [organization] will be converted to slashes on path transformation.
            ivyPattern '[organisation]/[module]/ivy-[revision].xml' //  The pattern to use for published Ivy descriptors.
            // === Use Maven compatible patterns to publish ===
            artifactPattern '[organisation]/[module]/[revision]/[artifact]-[revision](-[classifier]).[ext]' // The pattern to use for published artifacts.
            artifactDeploymentPatterns {
                // Comma or space-separated list of Ant-style patterns of files that will be included in publishing. Include/Exclude patterns are applied on the published file path before any exclude patterns.
                includePatterns 'p1=v1;p2=v2'
                excludePatterns 'p3=v3;p4=v4'
            }
            filterExcludedArtifactsFromBuild true //  Add the excluded files to the excludedArtifacts list and remove them from the artifacts list in the build info.
            deploymentProperties 'buildStatus=RC;platforms=win386,win64,osx,debian' //  Semicolon-separated list of properties to attach to all deployed artifacts in addition to the default ones (build.name, build.number, vcs.revision, etc.). Property values can take environment variables.

            passIdentifiedDownstream false // When checked, a build parameter named ARTIFACTORY_BUILD_ROOT with a value of ${JOB_NAME}-${BUILD_NUMBER} will be sent to downstream builds.

            // === Jira integration ===
            enableIssueTrackerIntegration false // When the Jenkins JIRA plugin is enabled, synchronize information about JIRA issues to Artifactory and attach issue information to build artifacts. This allows you to quickly identify build artifacts that contain a fix for a specific issue.
            aggregateBuildIssues false //  When the Jenkins JIRA plugin is enabled, include all issues from previous builds up to the latest build status defined in "Aggregation Build Status" (not including it). Usually set to "Released" to include all issues of the current version up to the last released build.
            aggregationBuildStatus 'Released' // Define the latest build status to stop the aggregation at
        }
    }
}