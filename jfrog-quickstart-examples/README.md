# JFrog QuickStart Guide Examples

The JFrog Platform is an enterprise-ready automated end-to-end DevOps platform, ideal for managing all of your software releases from code to production.

This repository contains two projects, Docker and Pipelines, setup to easily get you started with your JFrog cloud or self-hosted subscription. Follow the steps in the JFrog [cloud](https://www.jfrog.com/confluence/x/ZxTXBg) or [self-hosted](https://www.jfrog.com/confluence/x/cRzXBg) quickstart guides. These guides will introduce you to some of the basic functionality of the JFrog Platform and these JFrog solutions: 

* JFrog Artifactory: [Universal package management](https://www.jfrog.com/confluence/display/JFROG/Package+Management) and container registry in one, supporting all major packaging formats, build tools, and CI servers.
* JFrog Xray: [Open source security scanning and license compliance](https://www.jfrog.com/confluence/display/JFROG/Xray+Security+and+Compliance) enabling DevSecOps and ensuring application security throughout your SDLC.
* JFrog Pipelines: [CI/CD pipeline orchestration](https://www.jfrog.com/confluence/display/JFROG/Pipelines+Developer+Guide) all the way from code to production.

## Creating a Docker Repository in JFrog Artifactory 
This example repository contains a simple Dockerfile that can be used to exercise Docker repositories in Artifactory via the [cloud](https://www.jfrog.com/confluence/x/ZxTXBg) or [self-hosted](https://www.jfrog.com/confluence/x/cRzXBg) quickstart guides. 

Here's what you'll need:
* A local Artifactory Docker repository with the name "docker-quickstart-local"
* A remote Artifactory Docker repository with the name "docker-quickstart-remote"
* A virtual Artifactory Docker repository with the name "docker-quickstart", that includes the "docker-quickstart-local" and "docker-quickstart-remote" repositories with the "docker-quickstart-local" repository set as the *Default Deployment Repository*.

Learn more about using JFrog Artifactory as your [Docker Registry](https://www.jfrog.com/confluence/display/JFROG/Docker+Registry)

## Creating a CI/CD DevOps Pipeline in JFrog Pipelines

This example repository contains a simple pipeline definition that demonstrates how to use JFrog Pipelines via the [cloud quickstart guide](https://jfrog.com/). 

This example pipeline shows how to construct a workflow using:

* Multiple [Steps](https://www.jfrog.com/confluence/display/JFROG/Pipelines+Steps) with parallel steps
* Input and output [Resources](https://www.jfrog.com/confluence/display/JFROG/Pipelines+Resources)
* Input [Integrations](https://www.jfrog.com/confluence/display/JFROG/Pipelines+Integrations)
* Run and Pipeline [state management](https://www.jfrog.com/confluence/display/JFROG/Creating+Stateful+Pipelines) 

Learn more about [JFrog Pipelines](https://www.jfrog.com/confluence/display/JFROG/Pipelines+Quickstart)
