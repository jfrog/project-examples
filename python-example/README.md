# Python Example

## Overview
This example demonstrates how to build a Python project with Artifactory, while collecting build-info.

## Before Running the Example
### Set Up the Environment 
1. Make sure **Python** is installed and the **python** command is in your PATH.
2. Install **pip**. You can use the [Pip Documentation](https://pip.pypa.io/en/stable/installing/) and also [Installing packages using pip and virtual environments](https://packaging.python.org/guides/installing-using-pip-and-virtual-environments/)
3. Create three Pypi repositories in Artifactory - a local, remote and a virtual repository. You can use the [PyPi Repositories Documentation](https://www.jfrog.com/confluence/display/RTF/PyPI+Repositories).
* The remote repository should proxy *https://files.pythonhosted.org* (the default when creating a Pypi remote repository). 
* Name the virtual repository *pipy*.
* The virtual repository should include thr remote repository.
* The virtual repository should have the local repository set as the *Default Deployment Repository*.
4. Make sure **wheel** and **setuptools** are installed. You can use the [Installing Packages Documentation](https://packaging.python.org/tutorials/installing-packages/).
5. Make sure version 1.28.0 or above of [JFrog CLI](https://jfrog.com/getcli/) is installed.

### Validate the Setup
In your terminal, validate that the following commands work.
```console
Output Python version:
> python --version

Output pip version:
> pip --version

Verify wheel is installed:
> wheel -h

Verify setuptools is installed:
> pip show setuptools

Verify that virtual-environment is activated:
> echo $VIRTUAL_ENV

Output JFrog CLI version:
> jfrog --version
```

## Running the Example
CD to the root project directory

```console
Configure Artifactory:
> jfrog rt c

Configure the project's resolution repository. You shoud set the virtual repository you created.
> jfrog rt pipc

Install project dependencies with pip from Artifactory:
> jfrog rt pipi -r requirements.txt --build-name=my-pip-build --build-number=1 --module=jfrog-python-example

Package the project, create distribution archives (tar.gz and whl):
> python setup.py sdist bdist_wheel

Upload the packages to the pypi repository in Artifactory:
> jfrog rt u dist/ pypi/ --build-name=my-pip-build --build-number=1 --module=jfrog-python-example

Collect environment variables and add them to the build info:
> jfrog rt bce my-pip-build 1

Publish the build info to Artifactory:
> jfrog rt bp my-pip-build 1

Install published package by installing it from Artifactory using pip:
> jfrog rt pip-install jfrog-python-example

Validate package successfully installed:
> pip show jfrog-python-example
```

Learn about [Building Python Packages with JFrog CLI](https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory#CLIforJFrogArtifactory-BuildingPythonPackages).
