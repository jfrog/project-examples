MSBuild Project Example


# Preparations: 
1. Their are two ways to integrate with Artifactory plugin:
	a. Run the Solution as is.
	b. Delete the packages folder, and publish (to your nuget repository) the Artifactory nuget package from "nupkg\Artifactory.1.0.1.3.nupkg" 
	
# Configuration:
1. On the Solution level you can find the "artifactory.build" file.
2. Define configurations that overrides resolving and deployment.

# Running
Build the Solution  
