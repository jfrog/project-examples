## Deploy `docker-app` in Kubernetes cluster using helm chart
* Pull helm chart from Virtual helm repository of Artifactory
* Deploy `docker-app` in Kubernetes using downloaded helm chart

1.  On the Jenkins front page, click on Credentials -> System -> Global credentials -> Add Credentials
    Add your Artifactory credentials as the type Username with password, with the ID artifactory-credentials 
    ![Add_Artifactory_Credentials](../images/Add_Credentials.png)
    
2.  Create new Jenkins Pipeline Job.

3.  Create Helm repositories in Artifactory using [quick setup wizard](https://www.jfrog.com/confluence/display/RTF/Getting+Started#GettingStarted-OnboardingWizard).       

4.  Add String Parameters:
    *   IMAGE_TAG (String Parameter) : Domain of Artifactory docker registry 
		e.g `IMAGE_TAG : latest`
    *   SERVER_URL (String Parameter) : Artifactory Server URL<Br>
        e.g. `SERVER_URL -> http://35.225.27.231/artifactory`
    *   REPO (String Parameter) : Artifactory Helm repo<Br>
        e.g. `REPO -> helm`
    *   CREDENTIALS (Credentials Parameter) : Artifactory Credential<Br>
        e.g. `CREDENTIALS -> artifactory-credentials`
    	
5.  Copy [Jenkinsfile](Jenkinsfile) to Pipeline Script.

6.  To build it, press Build Now.

7.  Check your newly published build in build browser of Artifactory.

## Commands to deploy `docker-app` chart manually to K8S cluster.

For this example I am using custom helm client with Authentication support for remote charts repositories
Here is link to [PR](https://github.com/kubernetes/helm/pull/3206)

* Add Artifactory Helm repo with helm client
```
helm repo add artifactory $ART_URL/$HELM_REPO $ART_USERNAME $ART_PASSWORD
```

* Update local helm repo index
```
helm repo update
```

* Install `docker-app` in k8s
```
helm install my-docker-app artifactory/docker-app-chart
```
