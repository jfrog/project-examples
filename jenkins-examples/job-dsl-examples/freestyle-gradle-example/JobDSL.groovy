job("job-dsl-artifactory-freestyle-gradle-example") {
    scm {
        git("https://github.com/JFrog/project-examples.git", "master")
    }

    configure { node ->
        // === Configure the Gradle builder ===
        node / 'builders' << 'hudson.plugins.gradle.Gradle' {
            tasks 'aP' // Specify a list of Gradle tasks to be invoked.
            rootBuildScriptDir 'gradle-examples/gradle-example-minimal' // If your workspace has the top-level build.gradle in somewhere other than the module root directory, specify the path.
            gradleName GRADLE // Tool name from Jenkins configuration
        }

        // === Configure artifactory gradle job ===
        node / 'buildWrappers' << 'org.jfrog.hudson.gradle.ArtifactoryGradleConfigurator' {
            // === Resolver ===
            resolverDetails {
                artifactoryName SERVER_ID
                resolveReleaseRepository {
                    keyFromText 'libs-release'
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
            }
            // Optional - Override deployer credentials
            //deployerCredentialsConfig {
            //    credentialsId ARTIFACTORY_CREDENTIALS
            //}

            useArtifactoryGradlePlugin true // Check if project uses the Artifactory Gradle Plugin
            deployBuildInfo true // Check if you wish to publish build information to Artifactory.

            // === Include environment variables ===
            includeEnvVars true // Check if you wish to include all environment variables accessible by the builds process.
            envVarsPatterns {
                // Comma or space-separated list of environment variables that will be included as part of the published build info.
                // Environment variables may contain the * and the ? wildcards. Include patterns are applied before any exclude patterns.
                includePatterns ''
                excludePatterns '*password*,*secret*,*key*'
            }

            // === NonStagedBuilds ===
            allowPromotionOfNonStagedBuilds false // Check if you wish that the build promotion operation will be available to all successful builds instead of only staged ones.
            defaultPromotionTargetRepository '' // Default target repository for build promotion.
            pushToBintrayEnabledConfig true // Check if you wish that the Push to Bintray operation will be available to all successful builds instead of only staged ones.

            // === Discard old builds from Artifactory (requires Artifactory Pro) ===
            discardOldBuilds false // Automatically remove old builds stored in Artifactory according to Jenkins's configured policy for discarding old builds.
            discardBuildArtifacts true // Automatically remove build artifacts stored in Artifactory according to Jenkins's configured policy for discarding old builds.
            asyncBuildRetention false // Check for asynchronous build retention.

            // === Override build name ===
            overrideBuildName false // Check if you wish to override Artifactory default build name.
            customBuildName '' // Sets the new Artifactory build name.

            // === Publish artifacts to Artifactory ===
            deployArtifacts true // Check if you wish to publish produced build artifacts to Artifactory.
            deployMaven false // Check if you wish to publish Gradle-generated POM files to Artifactory. Note: Maven descriptors are always deployed according to the Maven layout convention.
            deployIvy false // Check if you wish to publish Gradle-generated ivy.xml descriptor files to Artifactory.
            ivyPattern '[organisation]/[module]/ivy-[revision].xml' //  The pattern to use for published Ivy descriptors.
            // === Use Maven compatible patterns to publish ===
            useMavenPatterns true // Whether to use the default Maven patterns when publishing artifacts and Ivy descriptors, or to use custom patterns. Dots in [organization] will be converted to slashes on path transformation.
            artifactPattern '[organisation]/[module]/[revision]/[artifact]-[revision](-[classifier]).[ext]' // The pattern to use for published artifacts.
            artifactDeploymentPatterns {
                // Comma or space-separated list of Ant-style patterns of files that will be included in publishing. Include/Exclude patterns are applied on the published file path before any exclude patterns.
                includePatterns 'p1=v1;p2=v2'
                excludePatterns 'p3=v3;p4=v4'
            }
            filterExcludedArtifactsFromBuild true //  Add the excluded files to the excludedArtifacts list and remove them from the artifacts list in the build info.
            deploymentProperties 'buildStatus=RC;platforms=win386,win64,osx,debian' //  Semicolon-separated list of properties to attach to all deployed artifacts in addition to the default ones (build.name, build.number, vcs.revision, etc.). Property values can take environment variables.

            passIdentifiedDownstream false // When checked, a build parameter named ARTIFACTORY_BUILD_ROOT with a value of ${JOB_NAME}-${BUILD_NUMBER} will be sent to downstream builds.

            // === Release management ===
            releaseWrapper {
                // Omit block to disable release management
                tagPrefix '' // VCS tags name/base URL
                releaseBranchPrefix 'REL-BRANCH-' // The prefix of the release branch name (applicable only to Git).
                targetRemoteName '' // The Git repository name from the SCM, or Git repository URL to use for pushing.
                releasePropsKeys '' // Properties in your project's 'gradle.properties' file whose value should change upon release.
                nextIntegPropsKeys '' // Properties in your project's 'gradle.properties' file whose value should change upon release, but also for work on the next integration/development version after the release has been created.
                alternativeTasks '' // Alternative tasks and options to execute for a Gradle build running as part of the release. If left empty, the build will use original tasks and options instead of replacing them.
                defaultReleaseStagingRepository '' // Sets the default repository for release staging.
                useReleaseBranch true // If checked, the Use Release Branch checkbox will be set by default in the Artifactory Release Staging page.
            }

            // === Jira integration ===
            enableIssueTrackerIntegration false // When the Jenkins JIRA plugin is enabled, synchronize information about JIRA issues to Artifactory and attach issue information to build artifacts. This allows you to quickly identify build artifacts that contain a fix for a specific issue.
            aggregateBuildIssues false //  When the Jenkins JIRA plugin is enabled, include all issues from previous builds up to the latest build status defined in "Aggregation Build Status" (not including it). Usually set to "Released" to include all issues of the current version up to the last released build.
            aggregationBuildStatus 'Released' // Define the latest build status to stop the aggregation at
        }
    }
}