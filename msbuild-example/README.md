MSBuild Project Example
==========================
## Overview
This example uses JFrog CLI for downloading dependencies, uploading artifacts and publishing build info.<br/>
The solution has two projects: _MsbuildExample_, _MsbuildLibrary_.<br/>
_MsbuildLibrary_ project creates a library file: `MsbuildLibrary.dll` which is being used by _MsbuildExample_ project.

Each project holds its own dependencies and artifacts configuration in the csproj files.

## Prequesites 
* Download and add [JFrog CLI](https://jfrog.com/getcli/) executable to the search path (Add JFrog CLI to PATH env var).
* Make sure msbuild installed. 
* Configure Artifactory URL and credentials using the following command: 
```
jfrog rt c
``` 
* Create a local generic repository in Artifactory named msbuild-local

## Running the example
### Build the MsbuildLibrary Project with Artifactory


Run the build target using the following command line in _MsbuildLibrary_ folder: 
```
msbuild /t:build
```

### Deploying MSbuildLibrary artifacts and build-info
Use the following command line in _MsbuildLibrary_ folder:
```
msbuild /p:ArtifactoryPublish=true
```

This command line will upload the project artifacts to Artifactory to be used later by _MsbuildExample_ project.<br/> 
Build-info will be created and will be available in Artifactory.

### Build the MSBuildExample Project with Artifactory
In order to build _MsbuildExample_, _MsbuildLibrary_ project artifacts must be built and published to Artifactory.<br/>
Run the build target using the following command line in _MsbuildExample_ folder:
``` 
msbuild /t:build
```

### Deploying MsbuildExample artifacts and build-info
Use the following command line in _MsbuildExample_ folder:
```
msbuild /p:ArtifactoryPublish=true
```
This command line will upload the project artifacts to Artifactory.<br/> 
Build-info will be created and will be available in Artifactory.

## Build script details

### JFrog CLI properties: 
The following snippet from the scproj file adds the default properties that JFrog CLI uses in order to download and upload artifacts and to publish build-info.
```
<Target Name="SetJFrogProperties" BeforeTargets="DownloadDependencies;PrepareForBuild" Condition=" '$(ArtifactoryPublish)' == 'true' ">
    <Message Text="Setting JFrog properties"/>
    <!--Build name property-->
    <CreateProperty Condition=" '$(BuildName)' == '' " Value="$(MSBuildProjectName)">
      <Output TaskParameter="Value" PropertyName="BuildName" />
    </CreateProperty>
    <!--Build number property-->
    <CreateProperty Condition=" '$(BuildNumber)' == '' " Value="$([System.DateTime]::UtcNow.Ticks)">
      <Output TaskParameter="Value" PropertyName="BuildNumber" />
    </CreateProperty>
    <!--Build flags-->
    <CreateProperty Value="--build-name=$(BuildName) --build-number=$(BuildNumber)">
      <Output TaskParameter="Value" PropertyName="BuildFlags" />
    </CreateProperty>
    <!--Output path pattern fix, use this property to upload all the artifacts in build OutputPath-->
    <CreateProperty Value="$([System.String]::Copy('$(OutputPath)').Replace('\','\\'))">
      <Output TaskParameter="Value" PropertyName="ArtifatsPatternPath" />
    </CreateProperty>
</Target>
```

Build name and build number properties can be overridden by adding the following properties to the msbuild command:
```
/p:BuildName=someBuildName
/p:BuildNumber=10
```

Default properties values: <br/>
**BuildName**: project name. <br/>
**BuildNumber**: current time in milliseconds. <br/>

### Downloading dependencies:
The following snippet from the scproj file configures which artifacts will downloaded in the build process and the download target path.
```
<Target Name="DownloadDependencies" BeforeTargets="PrepareForBuild">
	<Message Text="Downloading dependencies"/>
	<!--Download dependencies from Artifactory using JFrog CLI-->
	<!--Example:
	<Exec Command="jfrog rt download repository/path/to/files destination\path\ $(BuildFlags)"/>
	-->

	<Exec Command="jfrog rt download msbuild-local/MsbuildLibrary/bin/Debug/MsbuildLibrary.dll dependencies\ --flat=true $(BuildFlags)"/>
</Target>
```

In this example `MsbuildLibrary.dll` file will be downloaded to `dependencies` folder.<br/>
It is important to add `$(BuildFlags)` variable to the CLI command lines in order to save dependencies information to create the build-info.

##### Note
* Downloading dependecies might require adding _Reference_ to the downloaded artifacts, in this example we add `MsbuildLibrary.dll` as follows: 
```
<ItemGroup>
	<Reference Include="MsbuildLibrary">
	<HintPath>dependencies\MsbuildLibrary.dll</HintPath>
	</Reference>
</ItemGroup>
```

### Uploading artifacts:
The following snippet from the scproj file configures which artifacts will be uploaded when adding `/p:ArtifactoryPublish=true` property.

```
<Target Name="UploadArtifacts" AfterTargets="AfterBuild" Condition=" '$(ArtifactoryPublish)' == 'true' ">
	<Message Text="Upload artifacts"/>
	<!--Upload artifacts to Artifactory using JFrog CLI-->
	<!--Example:
	<Exec Command="jfrog rt upload local\\artifacts\\path repository/destination/path $(BuildFlags)"/>
	<Exec Command="jfrog rt upload $(ArtifatsPatternPath)\\*.dll repository/destination/path $(BuildFlags)"/>
	<Exec Command="jfrog rt upload $(ArtifatsPatternPath) repository/destination/path $(BuildFlags)"/>
	-->
    
	<Exec Command="jfrog rt upload $(ArtifatsPatternPath) msbuild-local/$(BuildName)/ --flat=false $(BuildFlags)"/>
</Target>
```

In this example we are using `$(ArtifatsPatternPath)` property to upload all the artifacts that were created in the $(OutputPath).

### Publishing build-info:
The following snippet from the scproj file publishing build info to Artifactory, the build info will hold all the information about dependencies, artifacts, environment (optional) and git (optional). 

```
<Target Name="PublishBuildInfo" AfterTargets="AfterBuild;UploadArtifacts" Condition=" '$(ArtifactoryPublish)' == 'true' ">
	<Message Text="Publishing build-info"/>
	<!--Optional build info commands-->
	<!--Example:
	<Exec Command="jfrog rt build-add-git $(BuildName) $(BuildNumber)"/>
	<Exec Command="jfrog rt build-collect-env $(BuildName) $(BuildNumber)"/>
	-->
	    
	<!--Publish build-info to Artifactory using JFrog CLI-->
	<Exec Command="jfrog rt build-publish $(BuildName) $(BuildNumber)"/>
</Target>
```

### JFrog Xray build scan:
The following snippet from the scproj file allows scanning build dependencies and artifacts using JFrog Xray to find security and license vulnerabilities.

```
<Target Name="ScanBuild" AfterTargets="AfterBuild;PublishBuildInfo" Condition=" '$(ArtifactoryPublish)' == 'true' ">
	<Message Text="Performing JFrog Xray build scan"/>
	<Exec Command="jfrog rt build-scan $(BuildName) $(BuildNumber)"/>
</Target>
```