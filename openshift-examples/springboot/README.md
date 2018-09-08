# How to build and deploy spring boot applications to OpenShift with JFrog Artifactory #

It is very easy to build and deploy applications developed using Spring Boot to an OpenShift kubernetes cluster with JFrog Artifactory as Kubernetes registry.

## Prerequisites Details

* OpenShift 3.6+
* Artifactory trial license [get one from here](https://www.jfrog.com/artifactory/free-trial/)

## Install Artifactory in OpenShift:

* Install Artifactory in OpenShift following documentation provided in [artifactory-docker-examples](https://github.com/jfrog/artifactory-docker-examples/tree/master/openshift/artifactory)

## Assumptions ##

1) You have a project on OpenShift created or granted access to one where you can create applications.

2) The BuildConfig.yaml uses  https://github.com/redhat-helloworld-msa/ola as an example for the Spring Boot application to be deployed to OpenShift. This will need to be replaced with the git repo of your spring boot application.

3) The BuildConfig.yaml relies on s2i builder that is used to build the spring boot application. This example uses fabric8/s2i-java docker image.
 There are many s2i java builder images that might be suitable for your project, some of them can work with both maven and gradle builds. You can also create your own s2i image to meet special requirements.

4) Please replace <RT_DOCKER_REPO> with artifactory docker repo


## Follow the follwing steps to configure OpenShift to build, package and deploy SpringBoot application

#### Step 1: Create OC Secrets

* Create OC secret to access Docker repository -
  *   *oc create secret docker-registry rt-docker-registry --docker-server=<RT_DOCKER_REPO> --docker-username=<YOUR_USER> --docker-password=<YOUR_PASSWORD> --docker-email=<YOUR_EMAIL>
*  Link docker secret to atleast 3 service accounts -
   *   *oc secrets add serviceaccount/default secrets/rt-docker-registry --for=pull*
   *   *oc secrets add serviceaccount/builder secrets/rt-docker-registry*
   *   *oc secrets add serviceaccount/deployer secrets/rt-docker-registry*


   * Create OC secret for your settings.xml that is configured to use artifactory and make it available for s2i builder
      *   *oc create secret generic settings-secret --from-file=settings.xml=./settings.xml*

      
   *  Link settings-secret to atleast 3 service accounts -
      *   *oc secrets add serviceaccount/default secrets/settings-secret --for=pull*
      *   *oc secrets add serviceaccount/builder secrets/settings-secret*
      *   *oc secrets add serviceaccount/deployer secrets/settings-secret*

#### Step 2: Build, Deploy, Service templates

*  Create a springboot project or of you have already created it, use it(oc project spring-boot-samples)


*  Build Config: *oc create -f springboot-rt-bc.yaml*

*  Deployment Config: *oc create -f springboot-rt-dc.yaml*

*  Service Creation: *oc create -f springboot-rt-svc.yaml*

*  Route  Creation: *oc create -f springboot-rt-route.yaml*

*  Get route and access application via route in browser: `oc get route`

From your OpenShift login you can monitor builds and deploys.
