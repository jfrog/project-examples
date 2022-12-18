package org.jfrog.example.jfrogandroid

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jfrog.artifactory.client.Artifactory
import org.jfrog.artifactory.client.ArtifactoryClientBuilder
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl.LOCAL

object JFrogHelper {

    private const val ARTIFACTORY_URL = "https://appupdate.jfrog.io/artifactory"
    private const val ARTIFACTORY_USER = "<USER>"
    private const val ARTIFACTORY_PASSWORD = "<PASSWORD>"

    fun checkArtifactoryDialog(activity: AppCompatActivity) = activity.lifecycle.coroutineScope.launch(Dispatchers.Main) {
        requestArtifactoryVersions()
    }

    suspend fun requestArtifactoryVersions() : String? {
        val artifactory: Artifactory = ArtifactoryClientBuilder.create()
            .setUrl(ARTIFACTORY_URL)
            .setUsername(ARTIFACTORY_USER)
            .setPassword(ARTIFACTORY_PASSWORD)
            .build() // TODO runtime error with "No static field INSTANCE of type Lorg/apache/http/conn/ssl/AllowAllHostnameVerifier"

        withContext(Dispatchers.Default) {
            val localRepoList = artifactory.repositories().list(LOCAL)

            val list = artifactory.storage().storageInfo.repositoriesSummaryList
            list.forEach {
                println("${it.repoKey} ${it.usedSpace}")
            }

            val searchItems = artifactory.searches()
                .repositories("AppStore")
                .artifactsByName("app-*.apk")
                .doSearch()

            searchItems.forEach {
                println("searchItems=${it.toString()}")
            }

            val searchLatest = artifactory.searches()
                .repositories("AppStore")
                .artifactsLatestVersion()
                .artifactsByName("app-*.apk")
                .doRawSearch()

            println("searchLatest=$searchLatest")
        }
        return null
    }

}
