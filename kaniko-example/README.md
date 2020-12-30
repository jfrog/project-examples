# Kaniko Example
## General
[Kaniko](https://github.com/GoogleContainerTools/kaniko) is a tool to build container images from a Dockerfile, inside a container or Kubernetes cluster.

This is an example showing how to collect build info while using Kaniko  container to build image from a docker file and push it to Artifactory.

### Preconditions to run the example
* In your terminal, validate that the following commands work.
    ```console
    Output Docker version
    > docker --version

    Output JFrog CLI version >= 1.43.0
    > jfrog --version
    ```

* Modify the `config.json` file with your Artifactory docker registry url and the user name and password present in base64

## Running the Example
* CD to the this example with your terminal.

* Replace `**ARTPROD.MYCOMPANY**` with your Artifactory Docker registry url and `**TARGET-REPOSITORY**` with the repository name where the image is stored in Artifactory.

    ```console
    Run Kaniko:
    > docker run --rm -v `pwd`:/workspace -v `pwd`/config.json:/kaniko/.docker/config.json:ro gcr.io/kaniko-project/executor:latest --dockerfile=Dockerfile --destination=**ARTPROD.MYCOMPANY**/hello-world:latest --image-name-with-digest-file=image-file-details

    Configure Artifactory:
    > jfrog rt c

    Collect image build info:
    > jfrog rt build-docker-create **TARGET-REPOSITORY** --image-file="image-file-details" --build-name="myBuild" --build-number=1

    Publish build info:
    > jfrog rt build-publish myBuild 1
    ```