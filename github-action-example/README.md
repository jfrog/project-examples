# Github Action Example
This is an example Github workflow, which uses the [Setup JFrog CLI Github Action](https://github.com/jfrog/setup-jfrog-cli).

## Running the Example
1. Create a new Github repository.
2. Make sure you have version **1.29.0** or above of [JFrog CLI](https://jfrog.com/getcli/) on your local machine, by running ```jfrog -v```.
3. Configure the details of your Artifactory server by running ```jfrog rt c```.
4. Using the server ID you configured, export the server details by running ```jfrog rt c export <Server ID>```.
5. Copy the generated token to the clipboard.
6. In the Github repository you created, create a [secret](https://help.github.com/en/articles/virtual-environments-for-github-actions#creating-and-using-secrets-encrypted-variables) named JF_ARTIFACTORY_SERVER_1. Set the value of the secret to be the generated token you copied to the clipboard.
7. Push the [workflow.yml](workflow.yml) file as part of this example, under `.github/workflows/` in your Github repository.
8. Watch the workflow running following any push to the repository.
