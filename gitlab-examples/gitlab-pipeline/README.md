# GitLab pipeline example
This is an example of a GitLab pipeline which uses JFrog CLI to integrate with Artifactory.
This sample uses a Gradle project, builds it, and pushes the artifact to Artifactory, including build info, environment variables.
It than uses JFrog Xray to scan the build for OSS vulnerabilities.

## Configuring the GitLab project
1. Create a GitLab project
2. Import a Gradle repository into the project (I used https://github.com/spring-projects/spring-petclinic)
3. Configure the following variables in GitLab (Settings->CI/CD->Variables):
   1. JFROG_PLATFORM_SERVER_ID - a unique server ID used to reference the server configuration from within the pipeline code
   2. JFROG_PLATFORM_URL - the JFrog platform server used
   3. JFROG_PLATFORM_USER - the user name used to access JFrog platform
   4. JFROG_PLATFORM_PASS - a password used to validate the user
   5. JFROG_PLATFORM_REPO - a virtual repository used to upload and download artifacts from JFrog platform

4. Create a new pipeline in GitLab and copy the content of gitlab-ci.yml file to it

*** Sensitive variables like user or password should be masked in order to make sure their value is not exposed in the pipeline logs.

## Running the pipeline
By default GitLab pipelines are triggered whenever a change is commited to the project repo.
However, you can manually trigger the pipeline by going to: CI/CD->Pipelines and press the "Run pipeline" button.
To view the pipeline execution logs navigate to: CI/CD->Jobs, and click the required job execution line.

