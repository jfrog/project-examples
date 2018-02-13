MSBuild Project Example
==========================
## Overview
This example demonstrates how to modify your .NET csproj file, so that it does the following:
1. Downloads the project depedencies from Artifactory before the build.
2. Uploads the project artifacts to Artifactory after the build.
3. Collects and publish build-info to Artifactory.

To achive this, we'll add JFrog CLI commands into the project's csproj file.<br/>

## Before You Start 
* Download [JFrog CLI](https://jfrog.com/getcli/) and add it to the search path (add the path to *jfrog.exe* to the PATH environment variable).
* Make sure msbuild is installed, and add it to the search path (add the *msbuild.exe* path to the PATH environment variable). 
* Configure your Artifactory URL and credentials using the following command: 
```
jfrog rt c
``` 
* From the Artifactory UI, create a local generic repository named *msbuild-local*.

## Structure
This solution includes two projects: _MsbuildExample_, _MsbuildLibrary_.<br/>
The _MsbuildLibrary_ project creates a library file: *MsbuildLibrary.dll* which is later used by _MsbuildExample_ project as a dependency.

## Running the Example
CD to the *MsbuildLibrary* directory and run the build target using the following command.
This will build the *MsbuildLibrary* project and generate the *MsbuildLibrary.dll* file.
```
msbuild /t:build
```
The next step should be publishing the *MsbuildLibrary.dll* file to Artifactory, along with
its build-info.
Run the following command to do this.
```
msbuild /p:ArtifactoryPublish=true
```
Now that we have our *MsbuildLibrary.dll* dependency ready in Artifactory, we can build
the *MsbuildExample* projects.
CD into the *MsbuildExample* directory and run the following command.
This will download *MsbuildLibrary.dll* from Artifactory and run the build to create its artifacts.
``` 
msbuild /t:build
```
We're almost done. All that's left to do it to publish the artifacts created by the *MsbuildExample*
project to Artifactory, along with its build-info.
```
msbuild /p:ArtifactoryPublish=true
```
## Understanding the Script

### Setting the Build Name, Build Number and Output Path  
The following snippet from the scproj file does the following:
1. Creates a variable named *BuildFlags* with the following value *--build-name=$(BuildName) --build-number=$(BuildNumber)"*.
The default values for the $(BuildName) and $(BuildNumber) are the project name and the current time respectively.
the build name and number can be set as arguments when running the msbuild command. For example:
```
msbuild /p:ArtifactoryPublish=true /p:BuildName=someBuildName /p:BuildNumber=10
```
2. Creates a variable named *ArtifatsPatternPath* with the project's output path as its value.

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

### Downloading Dependencies:
The following snippet downlaods the *MsbuildLibrary.dll* file from the *msbuild-local* Artifactory repository
into the dependencies directory.
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
##### Note
* The `MsbuildLibrary.dll` depedency should be added as a project *Refrence*. Here's how we did it.
```
<ItemGroup>
	<Reference Include="MsbuildLibrary">
	<HintPath>dependencies\MsbuildLibrary.dll</HintPath>
	</Reference>
</ItemGroup>
```
* In this specific example we're downloading only a single file from Artifactory (MsbuildLibrary.dll). You can modify the *jfrog rt download* command to download multiple files. You can also use File Specs to define the files to be downloaded. Read more about the download command [here](https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory#CLIforJFrogArtifactory-DownloadingFiles).

### Uploading Artifacts:
The following snippet uploads the artifacts to Artifactory, when adding `/p:ArtifactoryPublish=true` to the command.
We are using the `$(ArtifatsPatternPath)` property to upload all the artifacts built in the $(OutputPath).

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
Read more about the *jfrog rt upload* command [here](https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory#CLIforJFrogArtifactory-UploadingFiles).

### Publishing Build-Info:
The following snippet publishes the build-info to Artifactory. 

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
You can read more about the JFrog CLI commands used in the above script [here](https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory).
