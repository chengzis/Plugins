package io.github.chengzis.plugin.base



data class KotlinConfig(
    val map: Map<String, Any?>
) {

    val version: String by map

    companion object {

        fun fromConfig(config: Map<String, Any?>): KotlinConfig? {
            val data = ConfigUtil.getConfig(config, "kotlin")
            data?.addDefaultWhenKeyNotFound("version")
            return if (data == null) null else KotlinConfig(data)
        }
    }
}