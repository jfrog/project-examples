# Manifest List also known as "fat manifest"

## General

The manifest list is the “fat manifest” which references image manifests for platform-specific versions of an image.

This is an example showing how to collect build-info, for fat manifest images.
We will build a fat manifest image, push it to Artifactory and collect it's build-info.
For this demo, we will be using [buildx](https://github.com/docker/buildx) in order to build and push the fat manifest image into Artifactory.

### Prerequisites for running this example

- Make sure [JFrog CLI](https://jfrog.com/getcli/) 2.10.1 or above is installed and in your system PATH.
- Make sure [docker](https://docs.docker.com/get-docker/) is installed and in your system PATH.
- Make sure Artifactory can be used as docker registry. Please refer to [Getting Started with Artifactory as a Docker Registry](https://www.jfrog.com/confluence/display/JFROG/Getting+Started+with+Artifactory+as+a+Docker+Registry) in the JFrog Artifactory User Guide. You should end up with a docker registry URL, which is mapped to a local docker repository (or a virtual docker repository with a local deployment target) in Artifactory. You'll need to know the name of the docker repository, to later collect the published image build-info.

### Validating your setup

- In your terminal, validate that the following commands work.

  ```console
  Output docker version
  > docker --version

  Output JFrog CLI version 2.10.1 or above.
  > jfrog --version
  ```

## Running the Example

- Clone this repository.
- CD into this example directory.
- Run the following commands, after replacing **DOCKER_REG_URL** with your Artifactory Docker registry URL and **TARGET_REPO** with the target repository name in Artifactory. **TARGET_REPO** is the name of the Artifactory repository mentioned in the [Prerequisites for running this example](#prerequisites-for-running-this-example) section.

  ```console
  Build and push multi platforms image:
  > docker login DOCKER_REG_URL
  > docker buildx install
  > docker buildx create --use --name mybuilder --driver docker-container
  > docker buildx build --platform=linux/amd64,linux/arm64,linux/arm/v7 --tag=DOCKER_REG_URL/TARGET_REPO/multiarch-image:1 -f=Dockerfile.Fatmanifest --metadata-file=build-metadata --push .

  Configure Artifactory:
  > jf c add --url=<JFROG_PLATFORM_URL> [credentials flags]

  Collect image build info:
  > jf rt build-docker-create TARGET_REPO --server-id=MY_SERVER_ID --image-file build-metadata --build-name myBuild --build-number 1

  Publish build info:
  > jf rt build-publish --server-id=MY_SERVER_ID  myBuild 1

  Remove buildx alias
  > docker buildx uninstall
  ```
