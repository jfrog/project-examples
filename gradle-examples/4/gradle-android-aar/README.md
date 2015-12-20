Artifactory Plugin with Android Sample 
==========================

## Building

The build [Android SDK](http://developer.android.com/sdk/index.html)
should be installed in your development environment. In addition you'll need to set
the `ANDROID_HOME` environment variable to the location of your SDK:

```bash
export ANDROID_HOME=/opt/tools/android-sdk
```

After satisfying those requirements, the build is pretty simple:

* Run `gradlew artifactoryPublish` from the `app` directory to build the aar


