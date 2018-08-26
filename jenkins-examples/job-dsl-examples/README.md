## Introduction
The Job DSL plugin allows the programmatic creation of Jenkins jobs using a DSL.
For more information about the Jenkins Job DSL, refer to [Job DSL Tutorial](https://github.com/jenkinsci/job-dsl-plugin/wiki/Tutorial---Using-the-Jenkins-Job-DSL).

## Prerequisite
Jenkins Artifactory Plug-in v2.15.0 and above.

## Examples
The following examples will help you get started building the Job DSL seed jobs.

Follow these steps to run the examples:

### Step 1: Create a Job DSL seed job
Create a Job DSL seed job as instructed in the [Job DSL Tutorial](https://github.com/jenkinsci/job-dsl-plugin/wiki/Tutorial---Using-the-Jenkins-Job-DSL).

### Step 2: Configure an Artifactory server
All the examples use an Artifactory server defined in **Manage Jenkins** > **Configure System**.
The server is retrieved in the examples as follows:
```
resolverDetails {
    artifactoryName SERVER_ID
}
deployerDetails {
    artifactoryName SERVER_ID
}
```
To set the server ID, follow these steps in the seed job:
1. Find or configure an Artifactory server in Jenkins through **Manage Jenkins** > **Configure System**.
2. In the Jenkins seed job configuration running the example, enable **This project is parameterized**.
3. Click **Add Parameter** and then select **String Parameter**.
4. Set the **Name** as **SERVER_ID** and the **Default Value** as the Artifactory Server ID configured in **Manage Jenkins**.

### Step 3: Complete the seed job configuration
Complete the seed job configuration in your Jenkins seed job configuration:
1. In the **build** section add **Process Job DSLs**.
2. Select **Use the provided DSL script**.
3. Copy the content of the *JobDSL.groovy* example file to the **DSL Script** text area.

### Available Examples
* [freestyle-generic-example](freestyle-generic-example): Demonstrates a seed-job that creates a [freestyle-generic-job](https://www.jfrog.com/confluence/display/RTF/Jenkins+Artifactory+Plug-in#JenkinsArtifactoryPlug-in-ConfiguringGeneric(Freestyle)Builds) for downloads and uploads files using file specs.
* [freestyle-gradle-example](freestyle-gradle-example): Demonstrates a seed-job that creates a [freestyle-gradle-job](https://www.jfrog.com/confluence/display/RTF/Jenkins+Artifactory+Plug-in#JenkinsArtifactoryPlug-in-ConfiguringGradleBuilds).
* [freestyle-ivy-example](freestyle-ivy-example): Demonstrates a seed-job that creates a [freestyle-ivy-job](https://www.jfrog.com/confluence/display/RTF/Jenkins+Artifactory+Plug-in#JenkinsArtifactoryPlug-in-ConfiguringIvy-AntBuilds).
* [freestyle-maven-example](freestyle-maven-example): Demonstrates a seed-job that creates a [freestyle-maven-job](https://www.jfrog.com/confluence/display/RTF/Jenkins+Artifactory+Plug-in#JenkinsArtifactoryPlug-in-ConfiguringMaven2andMaven3Builds).
* [ivy-example](ivy-example): Demonstrates a seed-job that creates an [ivy-job](https://www.jfrog.com/confluence/display/RTF/Jenkins+Artifactory+Plug-in#JenkinsArtifactoryPlug-in-ConfiguringIvy-AntBuilds).
* [maven-example](maven-example): Demonstrates a seed-job that creates a [maven-job](https://www.jfrog.com/confluence/display/RTF/Jenkins+Artifactory+Plug-in#JenkinsArtifactoryPlug-in-ConfiguringMaven2andMaven3Builds).
* [pipeline-example](pipeline-example): Demonstrates a seed-job that creates a [pipeline-job](https://www.jfrog.com/confluence/display/RTF/Working+With+Pipeline+Jobs+in+Jenkins).

Learn more about the benefits of [Artifactory’s integration with Jenkins CI](https://jfrog.com/integration/jenkins-ci/).