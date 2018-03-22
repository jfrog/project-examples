package org.jfrog.example;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.ArtifactoryClientBuilder;
import org.jfrog.artifactory.client.model.File;
import org.jfrog.artifactory.client.model.LightweightRepository;
import org.jfrog.artifactory.client.model.RepoPath;
import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.repository.settings.impl.GenericRepositorySettingsImpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl.LOCAL;

public class ClientExample {

    //TODO: Insert your Artifactory URL and credentials
    private static String userName = "<YOUR_USERNAME>";
    private static String password = "<YOUR_PASSWORD>";
    private static String artifactoryUrl = "<ARTIFACTORY_URL>";

    private static String repoName = "artifactory_java_client_repo";
    private static String fileNameToUpload = "ex_upload_1.txt";
    private static String fileUploadToLocation = "ex_fold1/ex_upload_1.txt";
    private static String fileDownloadToLocation = "ex_download_1.txt";


    public static void main(String[] args) throws Exception {

        //create artifactory object
        Artifactory artifactory = createArtifactory(userName, password, artifactoryUrl);

        if (artifactory == null){
            throw new RuntimeException("artifactory creation failed");
        }

        //create repository
        String repositoryCreationResult = createNewRepository(artifactory, repoName);

        //create and upload a file
        File uploadedFile = uploadFile(artifactory, repoName, fileUploadToLocation, fileNameToUpload);

        //search for file
        List<RepoPath> searchResult = searchFile(artifactory, repoName, fileNameToUpload);

        //download file from artifactory
        java.io.File downloadedFile = downloadFile(artifactory, repoName, fileUploadToLocation, fileDownloadToLocation);


        System.out.print("Example finished.");
    }


    /**
     * This method creates an artifactory object
     */
    private static Artifactory createArtifactory(String username, String password, String artifactoryUrl) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(artifactoryUrl)){
            throw new IllegalArgumentException("Arguments passed to createArtifactory are not valid");
        }

        return ArtifactoryClientBuilder.create()
                .setUrl(artifactoryUrl)
                .setUsername(username)
                .setPassword(password)
                .build();
    }

    /**
     * This method checks whether repository with supplied name exists or not, and creates new if required.
     */
    private static String createNewRepository(Artifactory artifactory, String repoName) {
        if (artifactory == null || StringUtils.isEmpty(repoName)){
            throw new IllegalArgumentException("Arguments passed to createNewRepository are not valid");
        }

        List<LightweightRepository> repoList = artifactory.repositories().list(LOCAL);
        Set<String> repoNamesList = repoList.stream()
                .map(LightweightRepository::getKey)
                .collect(Collectors.toSet());

        String creationResult = null;
        if ( repoNamesList != null && !(repoNamesList.contains(repoName)) ){
            GenericRepositorySettingsImpl settings = new GenericRepositorySettingsImpl();
            Repository repository = artifactory.repositories()
                    .builders()
                    .localRepositoryBuilder()
                    .key(repoName)
                    .description("new example local repository")
                    .repositorySettings(settings)
                    .build();
            creationResult = artifactory.repositories().create(1, repository);
        }

        return creationResult;
    }

    /**
     * This method receives the uploaded file source and destination, performs the upload to artifactory
     */
    private static File uploadFile(Artifactory artifactory, String repo, String destPath, String fileNameToUpload) throws IOException {
        if (StringUtils.isEmpty(repo) || StringUtils.isEmpty(destPath) || StringUtils.isEmpty(fileNameToUpload) || artifactory == null){
            throw new IllegalArgumentException("Arguments passed to createArtifactory are not valid");
        }

        Path path = Paths.get(fileNameToUpload);
        Files.write(path, Collections.singleton("This is an example line"), Charset.forName("UTF-8"));

        java.io.File file = new java.io.File(fileNameToUpload);

        return artifactory.repository(repo).upload(destPath, file).doUpload();
    }

    /**
     * Search for file by name in a specific repository, return the location of file
     */
    private static List<RepoPath> searchFile(Artifactory artifactory, String repoName, String fileToSearch) {
        if (artifactory == null || StringUtils.isEmpty(repoName) || StringUtils.isEmpty(fileToSearch)){
            throw new IllegalArgumentException("Arguments passed to serachFile are not valid");
        }

        return artifactory.searches()
                .repositories(repoName)
                .artifactsByName(fileToSearch)
                .doSearch();
    }

    /**
     * Download the required file from artifactory
     */
    private static java.io.File downloadFile(Artifactory artifactory, String repo, String filePath, String fileDownloadToLocation) throws Exception {
        if (artifactory == null || StringUtils.isEmpty(repo) || StringUtils.isEmpty(filePath)){
            throw new IllegalArgumentException("Arguments passed to downloadFile are not valid");
        }

        InputStream inputStream = artifactory.repository(repo)
                .download(filePath)
                .doDownload();

        java.io.File targetFile = new java.io.File(fileDownloadToLocation);
        FileUtils.copyInputStreamToFile(inputStream, targetFile);

        return targetFile;
    }
}
