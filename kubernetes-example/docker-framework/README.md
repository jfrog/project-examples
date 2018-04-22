## Create a template Docker image
• Creates a base Docker image called ‘docker-framework’ using Ubuntu + Java + Tomcat
• Pushes it to a local repository in Artifactory

### Step to create Jenkins Pipeline:
<b>Note:</b> List of required Jenkins plugins
*   [Artifactory Plugin](https://wiki.jenkins.io/display/JENKINS/Artifactory+Plugin)   
*   [Docker Pipeline Plugin](https://wiki.jenkins.io/display/JENKINS/Docker+Pipeline+Plugin)   
*   [GitHub plugin](https://plugins.jenkins.io/git)   
*   [Pipeline Github Plugin](https://wiki.jenkins.io/display/JENKINS/Pipeline+Github+Plugin)   
*   [Pipeline Plugin](https://wiki.jenkins.io/display/JENKINS/Pipeline+Plugin)   

1.  On the Jenkins front page, click on Credentials -> System -> Global credentials -> Add Credentials
    Add your Artifactory credentials as the type Username with password, with the ID artifactory-credentials 
    ![Add_Artifactory_Credentials](../images/Add_Credentials.png)
    
2.  Create Following Docker repositories in Artifactory.
    `docker-stage-local` - Local docker repo.
    `docker-prod-local` - Local docker repo.
    `docker-remote`     - Remote docker repo pointing to Docker hub `https://registry-1.docker.io/`.
    `bintray-docker-remote` - Remote docker repo pointing to Bintray: `https://docker.bintray.io`. 
    `docker` - Virtual docker repo aggregating all above created repo with `docker-stage-local` as default repo for deployment.

3.  Create Generic local repository with name `tomcat-local` and deploy [Java](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and [Tomcat](https://archive.apache.org/dist/tomcat/tomcat-8/v8.0.32/bin/apache-tomcat-8.0.32.tar.gz) binaries in it. 

4.  Create new Jenkins Pipeline Job.

5.  Add String Parameters:
    *   ARTDOCKER_REGISTRY (String Parameter) : Domain of Artifactory docker registry 
		e.g `ARTDOCKER_REGISTRY : docker.artifactory`
    *   REPO (String Parameter) -> Artifactory virtual docker registry<Br>
		e.g.  `REPO -> docker`
    *   PROMOTE_REPO (String Parameter) : Artifactory production docker registry<Br>
	    e.g. `PROMOTE_REPO -> docker-prod-local`
    *   SOURCE_REPO (String Parameter) : Artifactory staging docker registry<Br>
    	e.g. `SOURCE_REPO -> docker-stage-local`
    *   SERVER_ID (String Parameter) : Artifactory Server Id<Br>
    	e.g. `SERVER_ID -> artifactory`
    *   XRAY_SCAN (Choice Parameter) : Xray Scan. Applicable only if you are using JFrog Xray<Br>
        e.g. `XRAY_SCAN -> YES`
    *   SERVER_URL (String Parameter) : Artifactory Server URL<Br>
        e.g. `SERVER_URL -> http://35.225.27.231/artifactory`
    *   CREDENTIALS (Credentials Parameter) : Artifactory Credential<Br>
        e.g. `CREDENTIALS -> artifactory-credentials`
    
6.  Copy [Jenkinsfile](Jenkinsfile) to Pipeline Script.

7.  To build it, press Build Now.

8.  Check your newly published build in build browser of Artifactory.
