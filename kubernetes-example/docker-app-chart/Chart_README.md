# Helm Chart for docker-app

### Configure docker registry credentials in K8S to pull docker image from Artifactory docker registry

## Create a Secret named regsecret:
```
kubectl create secret docker-registry regsecret --docker-server=<your-registry-server> --docker-username=<your-name> --docker-password=<your-pword> --docker-email=<your-email>
```
##### where:
```
<your-registry-server> is your Private Docker Registry FQDN.
<your-name> is your Docker username.
<your-pword> is your Docker password.
<your-email> is your Docker email.
```

##### Understanding your Secret

To understand what’s in the Secret you just created, start by viewing the Secret in YAML format:
```
kubectl get secret regsecret --output=yaml
```

## Installing the Chart

To install the chart with the release name `my-release`:

```console
$ helm install --name my-release ./
```

The command deploys docker-app on the Kubernetes cluster in the default configuration. The [configuration](#configuration) section lists the parameters that can be configured during installation.

> **Tip**: List all releases using `helm list`

## Uninstalling the Chart

To uninstall/delete the `my-release` deployment:

```console
$ helm delete my-release --purge
```

The command removes all the Kubernetes components associated with the chart and deletes the release.

## Configuration

The following tables lists the configurable parameters of the docker-app chart and their default values.

|           Parameter                |             Description             |                        Default                            |
|------------------------------------|-------------------------------------|-----------------------------------------------------------|
| `image.repository`                 | docker-app image                    | `$ART_DOCKER_REPO/docker-app:{tag}`                     |
| `image.pullPolicy`                 | Image pull policy                   | `Always`                                                  |
| `image.tag`                        | Tag of docker image                 | `latest`                                                  |
| `image.secretName`                 | Credentials of Art docker repo      | `regsecret`                                               |
| `service.type`                     | Kubernetes Service type             | `LoadBalancer`                                            |
| `service.port`                     | Port to expose                      | `8181`                                                    |
| `imageCredentials.registry`        | Artifactory docker registry         | `docker.artifactory`                                      |
| `imageCredentials.username`        | Artifactory username                | `admin`                                                   |
| `imageCredentials.password`        | Artifactory password                | `password`                                                |

Specify each parameter using the `--set key=value[,key=value]` argument to `helm install`. For example,

```console
$ helm install --name my-release --set image.tag=27 ./
```

Alternatively, a YAML file that specifies the values for the above parameters can be provided while installing the chart. For example,

```console
$ helm install --name my-release -f values.yaml ./
```

## Upgrade chart
```console
$ helm upgrade --name my-release --set image.tag=28 ./
```

