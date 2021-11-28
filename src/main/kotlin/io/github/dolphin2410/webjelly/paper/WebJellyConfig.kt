package io.github.dolphin2410.webjelly.paper

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files

data class WebJellyConfig(val map: HashMap<String, ArrayList<String>>): Map<String, ArrayList<String>> {
    constructor(): this(hashMapOf())

    companion object {
        @JvmStatic
        fun loadFrom(file: File): WebJellyConfig {
            FileSystems.newFileSystem(URI.create("jar:" + file.toPath().toUri()), hashMapOf("create" to "true")).use { fs ->
                val path = fs.getPath("webjelly.json")
                return if (Files.notExists(path)) {
                    WebJellyConfig()
                } else {
                    WebJellyConfig(HashMap<String, ArrayList<String>>().apply {
                        Json.parseToJsonElement(Files.readAllLines(path).joinToString())
                            .jsonObject.entries.forEach {
                                this[it.key] = ArrayList(it.value.jsonArray.map { item -> item.jsonPrimitive.content })
                            }
                    })
                }
            }
        }
    }

    override val entries: Set<Map.Entry<String, ArrayList<String>>>
        get() = map.entries.toSet()

    override val keys: Set<String>
        get() = map.keys

    override val size: Int
        get() = map.size

    override val values: Collection<ArrayList<String>>
        get() = map.values

    override fun containsKey(key: String): Boolean {
        return map.containsKey(key)
    }

    override fun containsValue(value: ArrayList<String>): Boolean {
        return map.containsValue(value)
    }

    override fun get(key: String): ArrayList<String>? {
        return map[key]
    }

    override fun isEmpty(): Boolean {
        return map.isEmpty()
    }
}