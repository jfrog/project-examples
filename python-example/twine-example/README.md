# Pip Example

## Overview
This example demonstrates how to build a Python Pip project with Artifactory, deploying it using Twine, while collecting build-info.

## Before Running the Example
### Set Up the Environment 
1. Make sure **Python** is installed and the **python** command is in your PATH.
2. Install **pip**. You can use the [Pip Documentation](https://pip.pypa.io/en/stable/installation/) and also [Installing packages using pip and virtual environments](https://packaging.python.org/guides/installing-using-pip-and-virtual-environments/)
3. Install **twine** by running `pip install twine`.
4. Create three Pypi repositories in Artifactory - a local, remote and a virtual repository. You can use the [PyPi Repositories Documentation](https://www.jfrog.com/confluence/display/RTF/PyPI+Repositories).
* The remote repository should proxy *https://files.pythonhosted.org* (the default when creating a Pypi remote repository). 
* Name the virtual repository *pypi*.
* The virtual repository should include the remote repository.
* The virtual repository should have the local repository set as the *Default Deployment Repository*.
5. Make sure version 2.68.0 or above of [JFrog CLI](https://jfrog.com/getcli/) is installed.

### Validate the Setup
In your terminal, validate that the following commands work.
```console
Output Python version:
> python --version

Output pip version:
> pip --version

Verify twine is installed:
> twine --version

Verify that virtual-environment is activated:
> echo $VIRTUAL_ENV

Output JFrog CLI version:
> jf --version
```

## Running the Example
'cd' to the root project directory

```console
Configure Artifactory:
> jf c add --url=<JFROG_PLATFORM_URL> [credentials flags]

Configure the project's resolution repository. You shoud set the virtual repository you created.
> jf pip-config --repo-resolve=<PYPI_REPO> --repo-deploy=<PYPI_REPO>

Install project dependencies with pip from Artifactory:

Using toml:
> jf pip install .
> python -m build --sdist --wheel

Using Setup.py:
> jf pip install . --build-name=my-pip-build --build-number=1 --module=jfrog-python-example
Package the project, create distribution archives (tar.gz and whl):
> python setup.py sdist bdist_wheel

Upload the packages to the pypi repository in Artifactory:
> jf twine upload "dist/*" --build-name=my-pip-build --build-number=1

Collect environment variables and add them to the build info:
> jf rt bce my-pip-build 1

Publish the build info to Artifactory:
> jf rt bp my-pip-build 1
```

Learn about [Building Python Packages with JFrog CLI](https://docs.jfrog-applications.jfrog.io/jfrog-applications/jfrog-cli/cli-for-jfrog-artifactory/package-managers-integration#building-python-packages).
