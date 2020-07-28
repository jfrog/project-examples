# Jenkins Scripted Pipeline Examples for Pip Build

## About the Examples

### pip-example
This example allows running the pip-install command inside a pip virtual-environment.
Provide the virtual-environment activation command in *'virtual_env_activation'*.
If you do not wish the pip-install execution to activate a virtual-environment for you, don't use the *'envActivation'* argument in *'rtPip.install'* execution.

### pip-plugin-example
This example demonstrates how to build your Python project with *pip* and *Artifactory*, while using the *[Pyenv Pipeline plugin](https://plugins.jenkins.io/pyenv-pipeline/)*.

## Python Package and Create Distribution Archives Stage
This part, in both of the examples, executes after the pip-install has completed, and doesn't involve Artifactory.
This example uses 'wheel' and 'setuptools' tools in this step, you can use any other packaging tool instead.

Refer to [Pip install with JFrog CLI example](https://github.com/jfrog/project-examples/tree/master/python-example) for information about setting the build environment.
