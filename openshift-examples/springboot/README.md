# How to build spring boot project and deploy to OpenShift with JFrog Artifactory #

Spring-boot applications are very commonly used to develop microservices. It is very
simple to deploy it to an OpenShift kubernetes cluster using JFrog Artifactory as
a docker registry.

Spring boot application can be developed with a github repository or a local git
repository. Developer can do continuous commits and trigger builds, but OpenShift
applications can also be built on these common trigger with artifacts being
deployed to artifactory as well. Here we lay out the step by step approach to accompish this task.

## Prerequisites Details

* OpenShift 3.6+
* Artifactory trial license [get one from here](https://www.jfrog.com/artifactory/free-trial/)

## Install Artifactory in OpenShift:

* Install Artifactory in OpenShift following documentation provided in [artifactory-docker-examples](https://github.com/jfrog/artifactory-docker-examples/tree/master/openshift/artifactory)

## Assumptions ##

1) You are the owner of or you have write privelges on a github repository or you have cloned a git repository locally and you can build and generate the target files yourself in your own branch.

2) The BuildConfig.yaml uses a s2i image builder available in docker hub at docker.io/jorgemoralespou/s2i-java:latest. This s2i builder image can be used for both maven and gradle builds. There are many s2i java builder images that might be suitable for your project. You can also create your own image.

3) In the spring-boot project root directory you have a Dockerfile that publsihes the fat jar to the docker repository. Please see an example here. https://github.com/redhat-helloworld-msa/ola. If not, s2i image needs to be able to push the built jar to docker repository

4) You have a project on OpenShift created or granted access to one where you can create applications.

5) In the commands in this README document and in the yaml files, please replace <RT_DECKER_REPO> with artifactory docker url.


## Follow the follwing steps to configure OpenShift to build, package and deploy SpringBoot application##

#### Step 1: Create OC Secrets

* Create OC secret to access Docker repository -
  *   *oc create secret docker-registry rt-docker-registry --docker-server=<RT_DOCKER_REPO> --docker-username=<YOUR_USER> --docker-password=<YOUR_PASSWORD> --docker-email=<YOUR_EMAIL>
*  Link docker secret to atleast 3 service accounts -
   *   *oc secrets add serviceaccount/default secrets/rt-docker-registry --for=pull*
   *   *oc secrets add serviceaccount/builder secrets/rt-docker-registry*
   *   *oc secrets add serviceaccount/deployer secrets/rt-docker-registry*

#### Step 2: Build, Deploy, Service templates

*  Create a springboot project or of you have already created it, use it(oc project spring-boot-samples) 


*  Build Config: *oc create -f springboot-rt-bc.yaml* 

*  Deployment Config: *oc create -f springboot-rt-dc.yaml*

*  Servce Creation: *oc create -f springboot-rt-svc.yaml*


From your OpenShift login you can monitor builds and deploys.

