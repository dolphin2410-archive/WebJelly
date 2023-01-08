package me.dolphin2410.webjelly

import me.dolphin2410.webjelly.maven.MavenArtifact
import me.dolphin2410.webjelly.maven.MavenRepository
import org.apache.maven.repository.internal.MavenRepositorySystemUtils
import org.eclipse.aether.RepositorySystem
import org.eclipse.aether.artifact.DefaultArtifact
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory
import org.eclipse.aether.repository.LocalRepository
import org.eclipse.aether.repository.RemoteRepository
import org.eclipse.aether.resolution.ArtifactRequest
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory
import org.eclipse.aether.spi.connector.transport.TransporterFactory
import org.eclipse.aether.transport.file.FileTransporterFactory
import org.eclipse.aether.transport.http.HttpTransporterFactory
import java.io.File

object WebJelly {
    @JvmStatic
    fun fetchArtifact(repository: MavenRepository, artifactName: MavenArtifact, folder: File = File(System.getProperty("java.io.tmpdir"))): File {
        return MavenRepositorySystemUtils.newServiceLocator().apply {
            addService(RepositoryConnectorFactory::class.java, BasicRepositoryConnectorFactory::class.java)
            addService(TransporterFactory::class.java, FileTransporterFactory::class.java)
            addService(TransporterFactory::class.java, HttpTransporterFactory::class.java)
        }.getService(RepositorySystem::class.java).run {
            resolveArtifact(
                MavenRepositorySystemUtils.newSession().apply {
                    localRepositoryManager = newLocalRepositoryManager(
                        this,
                        LocalRepository(folder.toString())
                    )
                },
                ArtifactRequest().apply {
                    artifact = DefaultArtifact(artifactName.toString())
                    repositories = arrayListOf(RemoteRepository.Builder("public", "default", repository.url.toString()).build())
                }
            ).artifact.file
        }
    }
}
