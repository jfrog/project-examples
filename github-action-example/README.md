# Github Action example

## Prerequisites

- JFrog CLI 1.29.0 and above.

## Instructions

1. On your machine, run `jfrog rt c export [Server ID]` to create a server token.
1. Create a [Github secret](https://help.github.com/en/articles/virtual-environments-for-github-actions#creating-and-using-secrets-encrypted-variables) named _RT_SERVER_TOKEN_. Put the value of the server token.
1. Put [workflow.yml](workflow.yml) under `.github/workflows/`.
1. Commit changes in Github and watch the build running.
