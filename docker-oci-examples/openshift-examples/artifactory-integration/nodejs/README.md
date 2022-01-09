# OpenShift Artifactory integration example with Node.js

## Prerequisites Details

* OpenShift 3.6+
* Artifactory trial license [get one from here](https://www.jfrog.com/artifactory/free-trial/)

## Install Artifactory in OpenShift:

* Install Artifactory in OpenShift following documentation provided in [artifactory-docker-examples](https://github.com/jfrog/artifactory-docker-examples/tree/master/openshift/artifactory)

## Create Node.js micro service 

* We will use [node-version](https://github.com/jainishshah17/node-version/) as an example project to demonstrate this integration.

#### Command to test `node-version` package:

* Set npm command line client to work with [Artifactory Npm Registry](https://www.jfrog.com/confluence/display/RTF/Npm+Registry).<br>
    create `.npmrc` file and paste following content to it:
    ```
    registry = https://$ARTIFACTORY_URL/api/npm/$ARTIFACTORY_NPM_REPO_NAME/
    _auth = $ARTIFACTORY_USER:$ARTIFACTORY_PASSWORD
    email = youremail@email.com
    always-auth = true
    ```
* Install dependencies: `npm install` <Br>
* Start node Server: `npm start` <Br>
* Access Application on: [http://localhost:3000](http://localhost:3000)

#### Command to build docker image and push it to Artifactory:

*   Build docker image: ```docker build -t $DOCKER_REGISTRY/node-version .```<Br>
*   Run docker container: ```docker run -d -p 3000:3000 $DOCKER_REGISTRY/node-version```<Br>
*   Login to Artifactory docker registry: ```docker login -u ARTIFACTORY_USER -p $ARTIFACTORY_PASSWORD $DOCKER_REGISTRY```<Br>
*   Push docker image: ```docker push $DOCKER_REGISTRY/node-version```


## Configure OpenShift to build, package and deploy node-version

#### Step 1: Create OC Secrets to use Artifactory as Docker Registry and NPM Repository

* Create OC secret to access Artifactory NPM repository given an existing `.npmrc` file: 
  * `oc create secret generic secret-npmr .npmrc=.npmrc`
* Create OC secret to access Docker repository:
  * `oc create secret docker-registry rt-docker-registry --docker-server=<RT_DOCKER_REPO> --docker-username=<YOUR_USER> --docker-password=<YOUR_PASSWORD> --docker-email=<YOUR_EMAIL>`
*  Link docker secret to at-least 3 service accounts:
   *   `oc secrets add serviceaccount/default secrets/rt-docker-registry --for=pull`
   *   `oc secrets add serviceaccount/builder secrets/rt-docker-registry`
   *   `oc secrets add serviceaccount/deployer secrets/rt-docker-registry`

#### Step 2: Create Nodejs project (Build, Deploy, Service and Route Creation templates)

**Note**: Replace `<RT_DOCKER_REPO>` with Artifactory docker registry domain in [Build Config](nodejs-rt-bc.yaml) and [Deployment Config](nodejs-rt-dc.yaml) before running following commands:

*  [Build Config](nodejs-rt-bc.yaml): `oc create -f nodejs-rt-bc.yaml`

*  [Deployment Config](nodejs-rt-dc.yaml): `oc create -f nodejs-rt-dc.yaml`

*  [Service Creation](nodejs-rt-svc.yaml): `oc create -f nodejs-rt-svc.yaml`

*  [Route Creation](nodejs-rt-route.yaml): `oc create -f nodejs-rt-route.yaml`

*  Get route and access application via route in browser: `oc get route` 


