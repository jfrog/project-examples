## This example and documentation are for the Beta v.1 of the Terraform Repositories, available exclusively to JFrog Beta customers.

# Terraform Example

## Overview
This example demonstrates how to publish terraform modules to Artifactory.

## Before Running the Example
### Set Up the Environment
1. Create local repository in Artifactory.
2. Make sure version 2.12.0 or above of [JFrog CLI](https://jfrog.com/getcli/) is installed.

### Validate the Setup
In your terminal, validate that the following commands work.
```console
Output JFrog CLI version:
> jfrog --version
```

### Scanning filesystem and packing modules
File system is being scanned from the current working directory.
A directory that contains at least one file with a “.tf” extension is considered as a terraform module and all its content will be packed in one zip file (including submodules directories) and will be deployed to Artifactory.
There won’t be a recursive scanning inside the module directory after packing, that means subnodules won’t be packed and deploy separately to Artifactory.

## Running the Example
CD to the root project directory

```console
Configure Artifactory:
> jf c add

Configure the project's deployment repository. You shoud set the local repository you created.
> jf rt tfc

CD to directory which contains the modules. for example "aws" directory.
> cd aws

Publish modules to Artifactory:
> jf tf p --namespace=example --provider=aws --tag=v0.0.1

To publish spesific modules cd into the module's root dierctory and run the publish command. module's name is determains by the directory name. 
> cd modules/module1
> jf tf p --namespace=example --provider=aws --tag=v0.0.2

To exclude files and directories (in this example, files which include "test" in their path) use --exclusions.
> jf tf p --namespace=example --provider=aws --tag=v0.0.3 --exclusions=*test*
```
