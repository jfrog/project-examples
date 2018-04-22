## Create Helm chart of product Docker image
* Create helm chart of [`docker-app`](Chart_README.md) 
* Push helm chart in local helm repository in Artifactory

1.  On the Jenkins front page, click on Credentials -> System -> Global credentials -> Add Credentials
    Add your Artifactory credentials as the type Username with password, with the ID artifactory-credentials 
    ![Add_Artifactory_Credentials](../images/Add_Credentials.png)
    
2.  Create new Jenkins Pipeline Job.

3.  Add String Parameters:
    *   IMAGE_TAG (String Parameter) : Domain of Artifactory docker registry 
		e.g `IMAGE_TAG : latest`
    *   SERVER_URL (String Parameter) : Artifactory Server URL<Br>
        e.g. `SERVER_URL -> http://35.225.27.231/artifactory`
    *   REPO (String Parameter) : Artifactory Helm repo<Br>
        e.g. `REPO -> helm`
    *   CREDENTIALS (Credentials Parameter) : Artifactory Credential<Br>
        e.g. `CREDENTIALS -> artifactory-credentials`
    
    	
4.  Copy [Jenkinsfile](Jenkinsfile) to Pipeline Script.

5.  To build it, press Build Now.

6.  Check your newly published build in build browser of Artifactory.
