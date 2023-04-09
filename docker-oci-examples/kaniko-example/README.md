# Kaniko Example
## General
[Kaniko](https://github.com/GoogleContainerTools/kaniko#kaniko---build-images-in-kubernetes) is a tool to build container images from a Dockerfile, inside a container or Kubernetes cluster.

This is an example showing how to collect build-info, while using the Kaniko container to build an image from a docker file and push it to Artifactory.

### Prerequisites for running this example
* Make sure [JFrog CLI](https://jfrog.com/getcli/) 1.43.0 or above is installed and in your system PATH.
* Make sure [docker](https://docs.docker.com/get-docker/) is installed and in your system PATH.
* Make sure Artifactory can be used as docker registry. Please refer to [Getting Started with Artifactory as a Docker Registry](https://www.jfrog.com/confluence/display/JFROG/Getting+Started+with+Artifactory+as+a+Docker+Registry) in the JFrog Artifactory User Guide. You should end up with a docker registry URL, which is mapped to a local docker repository (or a virtual docker repository with a local deployment target) in Artifactory. You'll need to know the name of the docker repository, to later collect the published image build-info.

### Validating your setup
* In your terminal, validate that the following commands work.
    ```console
    Output docker version
    > docker --version

    Output JFrog CLI version 1.43.0 or above.
    > jfrog --version
    ```

## Running the Example
* Clone this repository.
* CD into this example directory.
* Encode your Artifactory **username:password** using a base64 encoder, e.g. `echo -n  "admin:password" | base64`.
* Edit the 'config.json' file, located in the current directory.
* Replace the **USER-NAME:PASSWORD** token inside the file with the base64 encoded value.
* Replace the **DOCKER_REG_URL** token inside the file with your Artifactory docker registry URL.
* Run the following commands, after replacing **DOCKER_REG_URL** with your Artifactory Docker registry URL and **TARGET_REPO** with the target repository name in Artifactory. **TARGET_REPO** is the name of the Artifactory repository mentioned in the [Prerequisites for running this example](#prerequisites-for-running-this-example) section.

    ```console
    Run Kaniko:
    > docker run --rm -v `pwd`:/workspace -v `pwd`/config.json:/kaniko/.docker/config.json:ro gcr.io/kaniko-project/executor:latest --dockerfile=Dockerfile --destination=DOCKER_REG_URL/hello-world:1 --image-name-tag-with-digest-file=image-file-details

    Configure Artifactory:
    > jfrog c add --url=<JFROG_PLATFORM_URL> [credentials flags]

    Collect image build info:
    > jfrog rt build-docker-create TARGET_REPO --image-file image-file-details --build-name myBuild --build-number 1

    Publish build info:
    > jfrog rt build-publish myBuild 1
    ```
