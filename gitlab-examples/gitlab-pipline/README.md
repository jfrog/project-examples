# GitLab pipeline example
This is an example of a GitLab pipeline which uses JFrog CLI to integrate with artifactory.
This sample uses a gradle project, builds it, and push the artifact to artifactory, including build info, environment variables and SBOM.
It than uses JFrog Xray to scan the build for OSS vulnerabilities.

## Configuring the GitLab project
1. create a GitLab project
2. import a gradle repository into the project
3. configure the following variables in GitLab (Settings->CI/CD->Variables):
   1. ARTIFACTORY_SERVER_ID - a unique server ID used to reference the server configuration from within the pipeline code
   2. ARTIFACTORY_URL - the artifactory server used
   3. ARTIFACTORY_USER - the user name used to access artifactory
   4. ARTIFACTORY_PASS - a password or token used to validate the user
   5. ARTIFCATORY_REPO - a virtual repository used to upload and download artifacts from artifactory
4. Create a new pipeline in GitLab and copy the content of gitlab-ci.yml file to it

*** sensitive variables like user or password should be masked in order to make sure their value is not exposed in the pipeline logs

## Running the pipeline
By default GitLab pipelines are triggered whenever a change is commited to the project repo.
However, you can manually trigger the pipline by going to: CI/CD->Piplines and press the "Run pipeline" button.
to view the pipeline execution logs navigate to: CI/CD->Jobs, and click the required job execution line.

