# NPM Example

This is a sample project that resolves a dependency from Artifactory and deploys the build artifacts to Artifactory.

Running this example:
### Install dependencies
```console
> npm install
```

### Test package
```console
> npm test
```

### Authenticate user to be able to publish package and store output to .npmrc file.
```console
> curl -uadmin:APB3dEBJ8SaYKh96UBFu7QdN5HQ http://localhost:8081/artifactory/api/npm/auth
```


### Publish package to Artifactory
```console
> npm publish
```
