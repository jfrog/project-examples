import org.jfrog.build.api.Agent;
import org.jfrog.build.api.Artifact;
import org.jfrog.build.api.Build;
import org.jfrog.build.api.Module;
import org.jfrog.build.api.builder.ArtifactBuilder;
import org.jfrog.build.api.builder.BuildInfoBuilder;
import org.jfrog.build.api.builder.ModuleBuilder;
import org.jfrog.build.api.util.FileChecksumCalculator;
import org.jfrog.build.api.util.Log;
import org.jfrog.build.client.DeployDetails;
import org.jfrog.build.extractor.clientConfiguration.client.ArtifactoryBuildInfoClient;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * This code example uses the Artifactory build info project to create and deploy a build info with the build artifact.
 * Created by shaybagants on 11/1/15.
 */
public class CreateAndDeploy {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        String buildName = "build-test";
        String buildNumber = "2";
        String artifactoryURL = "http://localhost:8081/artifactory";
        String targetRepository = "libs-release-local";   //optional. Only used when deploying the actual artifacts (in addition to the build info).
        File artifactsDirectory = new File(args[0]); //directory location is passed by argument

        if (!artifactsDirectory.isDirectory()){
            throw new IOException(args[0]+" Path cannot be read, perhaps it does not exist, not a directory of there are not enough permissions to read it");
        }

        ArtifactoryBuildInfoClient client = new ArtifactoryBuildInfoClient(artifactoryURL, "admin", "password", new CreateAndDeploy.MyLog());
        BuildInfoBuilder builder = new BuildInfoBuilder(buildName);
        builder.number(buildNumber);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Build.STARTED_FORMAT);
        builder.started(simpleDateFormat.format(new Date()));

        ModuleBuilder moduleBuilder = new ModuleBuilder();
        moduleBuilder.id("test-module");
        List<File> listOfDirectoryArtifacts = new ArrayList<File>();
        listOfDirectoryArtifacts = getFilesFromDirectoryRecursively(artifactsDirectory.listFiles(), listOfDirectoryArtifacts);
        if (!listOfDirectoryArtifacts.isEmpty()) {
            for (File currentArtifact : listOfDirectoryArtifacts) {

                    HashMap<String, String> checksums = (HashMap) FileChecksumCalculator.calculateChecksums(currentArtifact, "MD5", "SHA1");
                    ArtifactBuilder artifactBuilder = new ArtifactBuilder("artifactBuilder");
                    Artifact artifact = artifactBuilder.sha1(checksums.get("SHA1")).md5(checksums.get("MD5")).name(currentArtifact.getName()).build();
                    moduleBuilder = moduleBuilder.addArtifact(artifact);

                    //if you don't want the actual artifact to be deployed and only interested to deploy the build info, please comment the below line.
                    deployArtifact(client, currentArtifact, artifactsDirectory.getAbsolutePath(), targetRepository, buildName, buildNumber);
            }
        }
        Module module = moduleBuilder.build();
        Build build = builder.addModule(module).agent(new Agent("Java-example-agent")).build();
        try {
            client.sendBuildInfo(build);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method deploys artifacts with their relative path to the artifactsDirectory. Return true if the deployment succeeded.
     */
    public static void deployArtifact(ArtifactoryBuildInfoClient client, File artifact, String baseDir, String targetRepository, String buildName, String buildNumber) throws IOException {
        Path artifactPath = Paths.get(artifact.getAbsolutePath());
        Path baseDirPath = Paths.get(baseDir);
        Path relativePath = baseDirPath.relativize(artifactPath).normalize();

        DeployDetails details = new DeployDetails.Builder().targetRepository(targetRepository)
                .artifactPath(relativePath.toString())
                .file(artifact)
                .addProperty("build.name", buildName)
                .addProperty("build.number", buildNumber).build();
        client.deployArtifact(details);
    }

    public static List getFilesFromDirectoryRecursively(File[] directory, List listOfDirectoryArtifacts) {
        for (File file : directory) {
            if (file.isDirectory()) {
                getFilesFromDirectoryRecursively(file.listFiles(), listOfDirectoryArtifacts);
            } else {
                listOfDirectoryArtifacts.add(file);
            }
        }
        return listOfDirectoryArtifacts;
    }


    public static class MyLog implements Log {
        @Override
        public void debug(String message) {
            System.out.println("Debug: " + message);
        }

        @Override
        public void info(String message) {
            System.out.println("Info: " + message);
        }

        @Override
        public void warn(String message) { System.out.println("Warn: " + message); }

        @Override
        public void error(String message) { System.out.println("Error: " + message); }

        @Override
        public void error(String message, Throwable e) { System.out.println("Error: " + message); }
    }


}


