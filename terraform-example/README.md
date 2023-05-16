# Terraform Example

## Overview
This example demonstrates how to publish terraform modules to Artifactory.

## Before Running the Example
### Set Up the Environment
1. Create a local Terraform repository in Artifactory.
2. Make sure version 2.12.0 or above of [JFrog CLI](https://jfrog.com/getcli/) is installed.

### Validate the Setup
In your terminal, validate that the following commands work.
```console
Output JFrog CLI version:
> jf --v
```

## Running the Example
'cd' to the root project directory

```console
Configure Artifactory:
> jf c add --url=<JFROG_PLATFORM_URL> [credentials flags]

Configure the project's deployment repository. You should set the local repository you created.
> jf terraform-config --repo-deploy=<TERRAFORM_REPO>

CD to directory which contains the modules. for example "aws" directory.
> cd aws

Publish modules to Artifactory:
> jf terraform publish --namespace=example --provider=aws --tag=v0.0.1

You can exclude files and directories from being scanned by the commands using the --exclusions option. In this example, files and directories which include test or ignore anywhere in their path, won't be scanned.
> jf terraform publish --namespace=example --provider=aws --tag=v0.0.2 --exclusions="*test*;*ignore*"
```

## How are the modules packed and published?
The jf tf command scans the local file-system under the current working directory recursively.
It searches for directories which includes at least one file with a .tf extension. Such a directory is assumed to be a terraform module, and it is therefore packed into one zip file (including submodules directories) and then published to Artifactory.
There isn't any recursive scanning inside the module directory after it is packed. This means that sub-nodules aren't packed and deployed separately to Artifactory.
