package io.github.chengzis.plugin.base

import org.gradle.api.Project
import org.yaml.snakeyaml.Yaml
import java.io.File


@Suppress("UNCHECKED_CAST")
object ConfigUtil {

    private const val CONFIG_FILE_KEY = "io.github.chengzis.plugin.config"
    private const val DEFAULT_CONFIG_FILE = "io.github.chengzis.plugin.config.yaml"

    fun loadConfig(project: Project): Map<String, Any?> {
        var config = findConfig(project)
        var parent: Project? = project.parent
        while (parent != null) {
            val parentConfig = findConfig(parent)
            config = mergeConfig(config, parentConfig)
            parent = parent.parent
            if (parent == null) break
        }
        return config

    }

    private fun findConfig(project: Project): Map<String, Any?> {
        val file = findConfigFile(project)
        if (file == null) return emptyMap()

        val yaml = Yaml()
        val result = file.inputStream().use {
            yaml.load(it) as Map<String, Any>
        }
        return result
    }


    private fun findConfigFile(project: Project): File? {
        val path = project.findProperty(CONFIG_FILE_KEY) ?: DEFAULT_CONFIG_FILE
        val file = project.file(path)
        if (file.exists()) {
            return file
        }
        return null
    }

    fun mergeConfig(child: Map<String, Any?>, parent: Map<String, Any?>): Map<String, Any?> {
        val result = mutableMapOf<String, Any?>()
        result.putAll(parent)

        child.keys.forEach { key ->
            if (child[key] == null) {
                return@forEach
            }
            when (val value = parent[key]) {
                is Map<*, *> -> {
                    val c = child[key] as? Map<String, Any?>
                    val p = value as Map<String, Any?>
                    if (c != null) {
                        result[key] = mergeConfig(c, p)
                    }
                }
                else -> {
                    result[key] = child[key]
                }
            }
        }
        return result
    }

    fun getConfig(config: Map<String, Any?>, key: String): MutableMap<String, Any?>? {
        val src = config[key] as? Map<String, Any?>
        if (src == null) return null
        return src.toMutableMap()
    }
}

fun MutableMap<String, Any?>.addDefaultWhenKeyNotFound(key: String) {
    if (containsKey(key)) return
    this[key] = null
}