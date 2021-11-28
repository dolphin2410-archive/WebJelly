package io.github.dolphin2410.webjelly.paper

import io.github.teamcheeze.jaw.reflection.ConstructorAccessor
import io.github.teamcheeze.jaw.reflection.FieldAccessor
import io.github.teamcheeze.jaw.reflection.MethodAccessor
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginLoader
import org.bukkit.plugin.SimplePluginManager
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File
import java.util.regex.Pattern

object WebJellyPluginLoader {

    @Suppress("unchecked_cast")
    fun loadPlugin(target: File, classLoader: ClassLoader): JavaPlugin {
        val fileAssociations = FieldAccessor(Bukkit.getPluginManager(), "fileAssociations").setDeclaringClass(
            SimplePluginManager::class.java).get() as Map<Pattern, PluginLoader>
        val filters = fileAssociations.keys.iterator()

        var plugin: JavaPlugin? = null

        while (filters.hasNext()) {
            val filter = filters.next()
            val match = filter.matcher(target.name)
            if (match.find()) {
                val loader = fileAssociations[filter] as JavaPluginLoader
                val description = loader.getPluginDescription(target)
                val libraryLoader: Any? = FieldAccessor(loader, "libraryLoader").setDeclaringClass(JavaPluginLoader::class.java).get()
                val createdLoader: Any? = libraryLoader?.let {
                    MethodAccessor(libraryLoader, "createLoader").invoke(description)
                }
                val pluginClassLoader = ConstructorAccessor(Class.forName("org.bukkit.plugin.java.PluginClassLoader"))
                    .newInstance(loader, classLoader, description, File(target.parentFile, description.name), target, createdLoader)

                (FieldAccessor(loader, "loaders").get() as MutableList<Any>).add(pluginClassLoader)

                plugin = FieldAccessor(pluginClassLoader, "plugin").get() as JavaPlugin
            }
        }

        return plugin ?: throw RuntimeException("Invalid Plugin")
    }
}