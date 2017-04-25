# Maven Example

This is a sample project that resolves a dependency from Artifactory and deploys the build artifacts to Artifactory.

Running this example:
### Install dependencies
```console
> mvn install
```

### Test package
```console
> mvn test
```

### Publish package to Artifactory

You have 2 ways to publish the package to Artifactory:
-Specificing repository element inside the distributionManagement in the POM.
-Using the -DaltDeploymentRepository parameter (with your data): 
```console
> mvn deploy -DaltDeploymentRepository=id::layout::url parameter
```

