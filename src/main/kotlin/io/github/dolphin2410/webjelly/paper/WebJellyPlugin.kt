package io.github.dolphin2410.webjelly.paper

import io.github.dolphin2410.webjelly.WebJelly
import io.github.dolphin2410.webjelly.maven.MavenArtifact
import io.github.dolphin2410.webjelly.maven.MavenRepository
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.net.URLClassLoader

@Suppress("unchecked_cast")
class WebJellyPlugin: JavaPlugin() {
    override fun onEnable() {
        dataFolder.mkdirs()
        dataFolder.listFiles()?.forEach { pluginFile ->
            val artifacts = ArrayList<File>()
            WebJellyConfig.loadFrom(pluginFile).forEach { entry ->
                entry.value.forEach { artifactName ->
                    artifacts.add(
                        WebJelly.fetchArtifact(
                            MavenRepository.from(entry.key),
                            MavenArtifact.from(artifactName)
                        )
                    )
                }
            }

            if (pluginFile.name.endsWith(".jar")) {
                val loader = URLClassLoader(artifacts.map { it.toURI().toURL() }.toTypedArray(), javaClass.classLoader.parent)
                val plugin = WebJellyPluginLoader.loadPlugin(pluginFile, loader)
                Bukkit.getPluginManager().enablePlugin(plugin)
            }
        }
    }

    override fun onDisable() {

    }
}