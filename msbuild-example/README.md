MSBuild Project Example
==========================

#### Note:
Major version zero (0.9.0) is for initial development. Anything may change at any time. This public API should not be considered stable.

==========================
### Preparations
* There are two ways to integrate with Artifactory plugin:
	* Run the Solution as is.
	* Delete the packages folder, and publish (to your nuget repository) the Artifactory nuget package from "nupkg\Artifactory.0.9.0.nupkg" 
	
### Configuration
* On the Solution level you can find the "artifactory.build" file, this is the main plugin configuration for
  all the projects under the solution.
* Also in depentency projects like Artifactory.DAL in our exmple, you can put the confiuration file,
  and config only the deployment patterns for that specific project.



### Running
Build the Solution  
