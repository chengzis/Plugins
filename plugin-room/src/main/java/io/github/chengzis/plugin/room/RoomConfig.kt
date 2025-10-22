package io.github.chengzis.plugin.room

import io.github.chengzis.plugin.base.ConfigUtil
import io.github.chengzis.plugin.base.Module
import io.github.chengzis.plugin.base.ModuleType
import io.github.chengzis.plugin.base.addDefaultWhenKeyNotFound

/**
 * Room插件扩展
 */
data class RoomConfig(
    val map: Map<String, Any?>
) {

    /**
     * 版本号
     */
    val version: String by map

    /**
     * 扩展功能
     */
    val extLibs: List<String>? by map

    /**
     * 启用将数据库架构导出到给定目录中的 JSON 文件的功能
     */
    val schemaLocation: String? by map

    /**
     * 启用 Gradle 增量注解处理器。默认值为 true
     */
    val incremental: Boolean? by map

    /**
     * 生成 Kotlin 源文件，而不是 Java 源文件。需要 KSP。
     * 自版本 2.7.0 起，默认值为 true
     */
    val generateKotlin: Boolean? by map

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun fromConfig(config: Map<String, Any?>): RoomConfig? {
            val data = ConfigUtil.getConfig(config, "room")
            data?.addDefaultWhenKeyNotFound("version")
            data?.addDefaultWhenKeyNotFound("extLibs")
            data?.addDefaultWhenKeyNotFound("schemaLocation")
            data?.addDefaultWhenKeyNotFound("incremental")
            data?.addDefaultWhenKeyNotFound("generateKotlin")
            return if (data == null) null else RoomConfig(data)
        }
    }
}

object RoomModule {

    private const val GROUP_ID = "androidx.room"

    internal val runtime = Module(
        groupId = GROUP_ID,
        artifactId = "room-runtime",
        type = ModuleType.MODULE
    )

    internal val compiler = Module(
        groupId = GROUP_ID,
        artifactId = "room-compiler",
        type = ModuleType.KSP_MODULE
    )

    val ktx = Module(
        groupId = GROUP_ID,
        artifactId = "room-ktx",
        type = ModuleType.MODULE
    )

    val rxjava2 = Module(
        groupId = GROUP_ID,
        artifactId = "room-rxjava2",
        type = ModuleType.MODULE
    )

    val rxjava3 = Module(
        groupId = GROUP_ID,
        artifactId = "room-rxjava3",
        type = ModuleType.MODULE
    )

    val guava = Module(
        groupId = GROUP_ID,
        artifactId = "room-guava",
        type = ModuleType.MODULE
    )

    val paging = Module(
        groupId = GROUP_ID,
        artifactId = "room-paging",
        type = ModuleType.MODULE
    )

    val testing = Module(
        groupId = GROUP_ID,
        artifactId = "room-testing",
        type = ModuleType.TEST
    )

    val all = listOf(ktx, rxjava2, rxjava3, guava, paging, testing)

}