pipeline {
    agent any

    stages {
        stage ('Clone') {
            steps {
                git branch: 'master', url: "https://github.com/talitz/spring-petclinic-ci-cd-k8s-example.git"
            }
        }

        stage ('Artifactory Configuration') {
            steps {
                rtServer (
                    id: "artifactory-server-id", 
                    url: "https://talyi.jfrog.io/artifactory",
                    credentialsId: "admin.jfrog"
                )

                rtMavenResolver (
                    id: 'maven-resolver',
                    serverId: 'artifactory-server-id',
                    releaseRepo: ARTIFACTORY_VIRTUAL_RELEASE_REPO,
                    snapshotRepo: ARTIFACTORY_VIRTUAL_SNAPSHOT_REPO
                )  
                 
                rtMavenDeployer (
                    id: 'maven-deployer',
                    serverId: 'artifactory-server-id',
                    releaseRepo: ARTIFACTORY_LOCAL_RELEASE_REPO,
                    snapshotRepo: ARTIFACTORY_LOCAL_SNAPSHOT_REPO,
                    threads: 6,
                    properties: ['BinaryPurpose=Technical-BlogPost', 'Team=DevOps-Acceleration']
                )
            }
        }
        
        stage('Build Maven Project') {
            steps {
                rtMavenRun (
                    tool: 'Maven 3.3.9',
                    pom: 'pom.xml',
                    goals: '-U clean install',
                    deployerId: "maven-deployer",
                    resolverId: "maven-resolver"
                )
            }
        }

        stage ('Build Docker Image') {
            steps {
                script {
                    docker.build("talyi-docker.jfrog.io/" + "pet-clinic:1.0.${env.BUILD_NUMBER}")
                }
            }
        }

        stage ('Push Image to Artifactory') {
            steps {
                rtDockerPush(
                    serverId: "artifactory-server-id",
                    image: "talyi-docker.jfrog.io/" + "pet-clinic:1.0.${env.BUILD_NUMBER}",
                    targetRepo: 'docker',
                    properties: 'project-name=jfrog-blog-post;status=stable'
                )
            }
        }

        stage ('Publish Build Info') {
            steps {
                rtPublishBuildInfo (
                    serverId: "helmRepoResource"
                )
            }
        }

        stage('Install Helm') {
            steps {
                  sh """
                    curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3
                    chmod 700 get_helm.sh && helm version
                  """
            }
        }

        stage('Configure Helm & Artifactory') {
            steps {
                 withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'admin.jfrog', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                   sh """
                    helm repo add helm https://talyi.jfrog.io/artifactory/helm --username ${env.USERNAME} --password ${env.PASSWORD}
                    helm repo update
                   """
                 }
            }
        }

        stage('Deploy Chart') {
            steps {
                withCredentials([kubeconfigContent(credentialsId: 'k8s-cluster-kubeconfig', variable: 'KUBECONFIG_CONTENT')]) {
                    sh """
                     echo "$KUBECONFIG_CONTENT" > config && cp config ~/.kube/config
                     helm upgrade --install spring-petclinic-ci-cd-k8s-example helm/spring-petclinic-ci-cd-k8s-chart --kube-context=gke_soleng-dev_us-west1-a_artifactory-ha-cluster --set=image.tag=1.0.${env.BUILD_NUMBER}
                    """
                }
            }
        }
    }
}
