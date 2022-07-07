![alt text](https://speedmedia.jfrog.com/08612fe1-9391-4cf3-ac1a-6dd49c36b276/https://media.jfrog.com/wp-content/uploads/2022/07/06131715/Creating-your-first-Pub-project-with-JFrog-Artifactory-863x300-1.png/mxw_1024,f_auto)

# Pub example part 2 
In this part we also creating a card package that we are gonna upload to our binary repository manager and use it as a dependecy to our project 

## Getting Started

This project is a starting point for a Flutter application.

A few resources to get you started if this is your first Flutter project:

- [Lab: Write your first Flutter app](https://flutter.dev/docs/get-started/codelab)
- [Cookbook: Useful Flutter samples](https://flutter.dev/docs/cookbook)

For help getting started with Flutter, view our
[online documentation](https://flutter.dev/docs), which offers tutorials,
samples, guidance on mobile development, and a full API reference.

# Artifactory & Pub intergration 
From JFrog Artifactory version 7.31.10, the [Pub repository](https://www.jfrog.com/confluence/display/JFROG/Pub+Repositories) is supported for the Dart programming language, which contains reusable libraries & packages for Flutter, Angular Dart, and general Dart programs.

# Local Pub Repositories
For the [private Dart packages](https://medium.com/dartlang/hosting-a-private-dart-package-repository-774c3c51dff9) that you will create and share only within your team or department, you should maintain local Pub repositories in Artifactory. You can create as many as you need, and specify their URLs in your pubspec file for your dependencies.

Read more about the local configuration [here] (https://jfrog.com/blog/how-to-use-pub-repositories-in-artifactory/)

# Remote Pub Repositories

As a Dart developer, you’ll rely on the growing pub.dev package repository for Flutter, Angular, and many other core services.

To help assure uninterrupted speed and consistency of your Dart builds and Flutter or Angular apps across teams, use an Artifactory remote repository to proxy pub.dev.

A remote repository in Artifactory serves as a read-only caching proxy for a repository or registry managed at a remote URL. There will be no difference between the contents of the remote repo and the native source.

# Virtual Pub Repositories

With Artifactory, you can combine any set of local and/or remote repositories into a (virtual repository) [https://www.jfrog.com/confluence/display/JFROG/Virtual+Repositories]  that acts as a single, logical repository that can be accessed through a common URL.


