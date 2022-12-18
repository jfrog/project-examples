package org.jfrog.example.jfrogandroid

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import junit.framework.Assert.assertNotNull

class JFrogUnitTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun requestArtifactoryVersions() = runTest {
        val result = JFrogHelper.requestArtifactoryVersions()
        assertNotNull(result)
    }
}