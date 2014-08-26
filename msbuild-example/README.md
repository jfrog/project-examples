MSBuild Project Example
==========================

### Preparations
* Their are two ways to integrate with Artifactory plugin:
	* Run the Solution as is.
	* Delete the packages folder, and publish (to your nuget repository) the Artifactory nuget package from "nupkg\Artifactory.1.0.1.3.nupkg" 
	
### Configuration
* On the Solution level you can find the "artifactory.build" file.
* Define configurations that overrides resolving and deployment.

### Running
Build the Solution  
