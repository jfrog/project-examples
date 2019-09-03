# Python example

## Overview
PyPi repositories are supported by Artifactory versions 5.8.10, 5.9.7, 5.10.4 or above (including 6.x).
Installing python project and producing the Build-Info is made using **pip**.

## Prerequisite
* Make sure **Python** is installed and the **python** command is in your PATH.
* Install **pip** from [pip documentation](https://pip.pypa.io/en/stable/installing/).
* Make sure your Artifactory version supports pypi repositories, setup repositories in Artifactory following [PyPi repositories configuration](https://www.jfrog.com/confluence/display/RTF/PyPI+Repositories#PyPIRepositories-Configuration).
* For packaging **Python** projects you need to have **wheel** and **setuptools** installed, read more about [Packaging Python Projects](https://packaging.python.org/tutorials/packaging-projects/).
* Best practice is to install **Python** projects with **pip** and virtual-environments, follow [this guide](https://packaging.python.org/guides/installing-using-pip-and-virtual-environments/).
* Install [JFrog CLI](https://jfrog.com/getcli/), version 1.28.0 or above.

#### Before Running the Example
In your terminal, validate that the following commands work:
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

Configure the project's resolution repository:
> jfrog rt pipc

Install project dependencies with pip from Artifactory:
> jfrog rt pipi -r requirements.txt --build-name=my-pip-build --build-number=1 --module=jfrog-python-example

Package the project, create distribution archives (tar.gz and whl):
> python setup.py sdist bdist_wheel

Upload the packages to the pypi repository in Artifactory:
> jfrog rt u dist/ pypi-local/ --build-name=my-pip-build --build-number=1 --module=jfrog-python-example

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