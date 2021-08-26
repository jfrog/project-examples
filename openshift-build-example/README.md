# OpenShift Build Example
## General
This is an example showing how to add a BuildConfig in OpenShift, using it to build and push a new image, while collecting build-info.

### Prerequisites for Running This Example
* Make sure [JFrog CLI](https://jfrog.com/getcli/) 2.4.0 or above is installed and in your system PATH.
* Make sure [OpenShift CLI](https://docs.openshift.com/container-platform/latest/cli_reference/openshift_cli/getting-started-cli.html#installing-openshift-cli) 3.0.0 or above is installed and in your system PATH.
* Make sure Artifactory can be used as Docker registry. Please refer to [Getting Started with Artifactory as a Docker Registry](https://www.jfrog.com/confluence/display/JFROG/Getting+Started+with+Artifactory+as+a+Docker+Registry) in the JFrog Artifactory User Guide. You should end up with a Docker registry URL, which is mapped to a local Docker repository (or a virtual Docker repository with a local deployment target) in Artifactory. You'll need to know the name of the Docker repository, to later collect the published image build-info.

## Running the Example
* Log in to the OpenShift CLI ([instructions in OpenShift documentation](https://docs.openshift.com/container-platform/latest/cli_reference/openshift_cli/getting-started-cli.html#cli-logging-in_cli-developer-commands)). If you get notified that you don't have any projects, follow the instructions to create a new one.
* Create a credentials secret for Artifactory. Replace the placeholders and run:
  ```
  oc create secret docker-registry rt-docker-registry --docker-server=<Docker repository address> --docker-username=<Artifactory username> --docker-password=<Artifactory password>
  ```
* Download build-config.yaml to your computer and replace the placeholder `<Docker repository address>` with the address of your Docker repository in Artifactory.
* Apply the build config YAML to create a new build config in OpenShift:
  ```
  oc apply -f ./build-config.yaml
  ```
* Configure JFrog CLI:
  ```
  jfrog config add
  ```
* Use JFrog CLI to start a new build from the build config we just created and collect build info. Replace the placeholders and run:
  ```
  jfrog rt oc-start-build jfrog-oc-build-example --server-id=<JFrog CLI config server ID> --repo=<Docker repository name> --build-name=oc-build-example --build-number=1
  ```
* Publish build info to Artifactory. Replace the placeholder and run:
  ```
  jfrog rt build-publish oc-build-example 1 --server-id=<JFrog CLI config server ID>
  ```
