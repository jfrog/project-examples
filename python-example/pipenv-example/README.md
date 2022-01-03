# Pipenv Example

## Overview
This example demonstrates how to build a Python Pipenv project with Artifactory, while collecting build-info.

## Before Running the Example
### Set Up the Environment 
1. Make sure **Python** is installed and the **python** command is in your PATH.
2. Install **pipenv**. You can use the [Pip Documentation](https://pipenv.pypa.io/en/latest/) and also [Installing packages using pip and virtual environments](https://realpython.com/pipenv-guide/)
3. Create three Pypi repositories in Artifactory - a local, remote and a virtual repository. You can use the [PyPi Repositories Documentation](https://www.jfrog.com/confluence/display/RTF/PyPI+Repositories).
* The remote repository should proxy *https://files.pythonhosted.org* (the default when creating a Pypi remote repository). 
* Name the virtual repository *pipy*.
* The virtual repository should include the remote repository.
* The virtual repository should have the local repository set as the *Default Deployment Repository*.
4. Make sure **wheel** and **setuptools** are installed. You can use the [Installing Packages Documentation](https://packaging.python.org/tutorials/installing-packages/).
5. Make sure version 2.9.0 or above of [JFrog CLI](https://jfrog.com/getcli/) is installed.

### Validate the Setup
In your terminal, validate that the following commands work.
```console
Output Python version:
> python --version

Output pipenv version:
> pipenv --version

Output JFrog CLI version:
> jfrog --version
```

## Running the Example
CD to the root project directory

```console
Configure Artifactory:
> jfrog c add

Configure the project's resolution repository. You shoud set the virtual repository you created.
> jfrog pipec

Install project dependencies with pip from Artifactory:
> jfrog pipenv install --build-name=my-pipenv-build --build-number=1 --module=jfrog-pipenv-example

Package the project, create distribution archives (tar.gz and whl):
> python setup.py sdist bdist_wheel

Upload the packages to the pypi repository in Artifactory:
> jfrog rt u dist/ pypi/ --build-name=my-pipenv-build --build-number=1 --module=jfrog-pipenv-example

Collect environment variables and add them to the build info:
> jfrog rt bce my-pipenv-build 1

Publish the build info to Artifactory:
> jfrog rt bp my-pipenv-build 1

Install published package by installing it from Artifactory using pip:
> jfrog pipenv install jfrog-pipenv-example

Learn about [Building Python Packages with JFrog CLI](https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory#CLIforJFrogArtifactory-BuildingPythonPackages).
