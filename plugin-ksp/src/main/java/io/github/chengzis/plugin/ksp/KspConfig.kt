package io.github.chengzis.plugin.ksp

import io.github.chengzis.plugin.base.ConfigUtil
import io.github.chengzis.plugin.base.addDefaultWhenKeyNotFound


/**
 * 最新版本号
 */
internal val latestKspVersion = "2.+"

data class KspConfig(
    val map: Map<String, Any?>
) {
    val version: String by map

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun fromConfig(config: Map<String, Any?>): KspConfig? {
            val data = ConfigUtil.getConfig(config, "ksp")
            data?.addDefaultWhenKeyNotFound("version")
            return if (data == null) null else KspConfig(data)
        }
    }
}

internal fun kotlinVersion2KspVersion(version: String): String? {
    when {
        version.startsWith("2.0.0") -> return "2.0.0-+"
        version.startsWith("1.9.23") -> return "1.9.23-+"
        version.startsWith("1.9.22") -> return "1.9.22-+"
        version.startsWith("1.9.21") -> return "1.9.21-+"
        version.startsWith("1.9.20") -> return "1.9.20-+"
        version.startsWith("1.9.10") -> return "1.9.10-+"
        version.startsWith("1.9.0") -> return "1.9.0-+"
        version.startsWith("1.8") -> return "1.8.0-+"
        version.startsWith("1.7") -> return "1.7.0-+"
        version.startsWith("1.6.21") -> return "1.6.21-+"
        version.startsWith("1.6.20") -> return "1.6.20-+"
        version.startsWith("1.6.10") -> return "1.6.10-+"
        version.startsWith("1.6.0") -> return "1.6.0-+"
        version.startsWith("1.5.31") -> return "1.5.31-+"
        version.startsWith("1.5.30") -> return "1.5.30-+"
        version.startsWith("1.5") -> throw IllegalStateException("Kotlin version $version is not supported")
        version.startsWith("1.4") -> throw IllegalStateException("Kotlin version $version is not supported")
        version.startsWith("1.3") -> throw IllegalStateException("Kotlin version $version is not supported")
        version.startsWith("1.2") -> throw IllegalStateException("Kotlin version $version is not supported")
        version.startsWith("1.1") -> throw IllegalStateException("Kotlin version $version is not supported")
        version.startsWith("1.0") -> throw IllegalStateException("Kotlin version $version is not supported")
        else -> return null
    }
}