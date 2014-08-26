param($installPath, $toolsPath, $package)

write-host "Artifactory Package Install Script start"

write-host "Copying Artifactory.build to Solution"

$rootDir = (Get-Item $installPath)

$solution = Get-Interface $dte.Solution ([EnvDTE80.Solution2])
$solutionDirectory = Split-Path -parent $solution.FileName

$fileFrom = join-path $rootDir '\artifactory\artifactory.build'

$fileTo = join-path $solutionDirectory '\artifactory.build'

Copy-Item $fileFrom $solutionDirectory

$vsProject = $solution.AddSolutionFolder("Artifactory_items")

$parentProjectFolder = Get-Interface $vsProject.ProjectItems ([EnvDTE.ProjectItems])

$projectFile = $parentProjectFolder.AddFromFile($fileTo)

write-host "Updating NuGet.targets file..."



$nugetPath = join-path $solutionDirectory '\.nuget\NuGet.targets'

$doc = [System.Xml.XmlDocument](Get-Content $nugetPath); 

$resolvePath = '$(solutionDir)' + '\packages\Artifactory.1.0.1.3\artifactory\resolve.targets'

$child = $doc.Project.AppendChild($doc.CreateElement("Import"))
$child.SetAttribute("Project",$resolvePath);
$child.SetAttribute("Condition","Exists('$resolvePath')");

$doc = [xml] $doc.OuterXml.Replace(" xmlns=`"`"", "")
$doc.Save($nugetPath);

try
{
	$url = "https://www.jfrog.com/"
	$dte2 = Get-Interface $dte ([EnvDTE80.DTE2])
	
	$dte2.ItemOperations.Navigate($url) | Out-Null
}
catch
{

}

write-host "Artifactory Package Install Script end"



