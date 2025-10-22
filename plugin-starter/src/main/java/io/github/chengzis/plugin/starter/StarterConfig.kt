package io.github.chengzis.plugin.starter

import io.github.chengzis.plugin.base.ConfigUtil
import io.github.chengzis.plugin.base.addDefaultWhenKeyNotFound

/**
 * Startup插件扩展
 */
data class StartupConfig(
    val map: Map<String, Any?>
) {

    /**
     * 扩展功能
     */
    val plugins: List<String>? by map

    companion object {

        fun fromConfig(config: Map<String, Any?>): StartupConfig? {
            val data = ConfigUtil.getConfig(config, "starter")
            data?.addDefaultWhenKeyNotFound("plugins")
            return if (data == null) null else StartupConfig(data)
        }
    }
}