# Pipenv Example

## Overview
This example demonstrates how to build a Python Poetry project with Artifactory, while collecting build-info.

## Before Running the Example
### Set Up the Environment 
1. Make sure **Python** is installed and the **python** command is in your PATH.
2. Install **poetry**. You can use the [Poetry Documentation](https://python-poetry.org/docs/).
3. Create three Pypi repositories in Artifactory - a local, remote and a virtual repository. You can use the [PyPi Repositories Documentation](https://www.jfrog.com/confluence/display/RTF/PyPI+Repositories).
* The remote repository should proxy *https://files.pythonhosted.org* (the default when creating a Pypi remote repository). 
* Name the virtual repository *pypi*.
* The virtual repository should include the remote repository.
* The virtual repository should have the local repository set as the *Default Deployment Repository*.
4. Make sure version 2.27.0 or above of [JFrog CLI](https://jfrog.com/getcli/) is installed.

### Validate the Setup
In your terminal, validate that the following commands work.
```console
Output Python version:
> python --version

Output pipenv version:
> poetry --version

Output JFrog CLI version:
> jf --version
```

## Running the Example
'cd' to the root project directory

```console
Configure Artifactory:
> jf c add

Configure the project's resolution repository. You shoud set the virtual repository you created.
> jf poetry-config --repo-resolve=<PYPI_REPO> [credentials flags]

Install project dependencies with poetry from Artifactory:
> jf poetry install --build-name=my-poetry-build --build-number=1 --module=jfrog-poetry-example

Package the project, create distribution archives (tar.gz and whl):
> jf build

Upload the packages to the pypi repository in Artifactory:
> jf rt u dist/ pypi/ --build-name=my-poetry-build --build-number=1 --module=jfrog-poetry-example

Collect environment variables and add them to the build info:
> jf rt bce my-poetry-build 1

Publish the build info to Artifactory:
> jf rt bp my-poetry-build 1

Install published package by installing it from Artifactory using pip:
> jf poetry install jfrog-pipenv-example

Learn about [Building Python Packages with JFrog CLI](https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory#CLIforJFrogArtifactory-BuildingPythonPackages).
