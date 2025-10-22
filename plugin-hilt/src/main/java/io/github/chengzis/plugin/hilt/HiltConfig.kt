package io.github.chengzis.plugin.hilt

import io.github.chengzis.plugin.base.ConfigUtil
import io.github.chengzis.plugin.base.Module
import io.github.chengzis.plugin.base.ModuleType
import io.github.chengzis.plugin.base.addDefaultWhenKeyNotFound

/*******************    💫 Codegeex Suggestion    *******************/
/**
 * Hilt插件扩展
 * 这是一个继承自Extension<Hilt>的开放类，用于配置Hilt相关设置
 *
 * 核心功能：
 * - 提供Hilt版本配置
 * - 支持扩展库版本配置
 * - 支持自定义扩展库列表
 *
 * 构造函数参数：
 * - version: Hilt主版本号，必需参数
 * - extLibsVersion: 扩展库版本号，可选参数，默认与version相同
 * - extLibs: 自定义扩展库列表，可选参数，默认为空列表
 *
 * 使用示例：
 * ```kotlin
 * val hilt = Hilt(
 *     version = "2.42",
 *     extLibsVersion = "1.0",
 *     extLibs = listOf("hilt-android", "hilt-compiler")
 * )
 * ```
 *
 * 注意事项：
 * - extLibsVersion如果不指定，将默认使用version的值
 * - extLibs列表中的库名称需要与Hilt官方库名称一致
 */
data class HiltConfig(
    val map: Map<String, Any?>
) {
    val version: String by map
    val extLibsVersion: String? by map
    val extLibs: List<String>? by map

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun fromConfig(config: Map<String, Any?>): HiltConfig? {
            val data = ConfigUtil.getConfig(config, "hilt")
            data?.addDefaultWhenKeyNotFound("version")
            data?.addDefaultWhenKeyNotFound("extLibsVersion")
            data?.addDefaultWhenKeyNotFound("extLibs")
            return if (data == null) null else HiltConfig(data)
        }
    }
}

/****************  c4baface3c0743668ba506dc8d6619ac  ****************/

object HiltModule {

    private const val GROUP_ID = "com.google.dagger"

    internal val runtime = Module(
        groupId = GROUP_ID,
        artifactId = "hilt-android",
        type = ModuleType.MODULE
    )

    internal val compiler = Module(
        groupId = GROUP_ID,
        artifactId = "hilt-android-compiler",
        type = ModuleType.KSP_MODULE
    )

    val navigationFragment = Module(
        groupId = "androidx.hilt",
        artifactId = "hilt-navigation-fragment",
        type = ModuleType.MODULE
    )

    val navigationCompose = Module(
        groupId = "androidx.hilt",
        artifactId = "hilt-navigation-fragment",
        type = ModuleType.MODULE
    )

    val navigationWork = Module(
        groupId = "androidx.hilt",
        artifactId = "hilt-navigation-work",
        type = ModuleType.MODULE
    )

    val lifecycleViewModel = Module(
        groupId = "androidx.hilt",
        artifactId = "hilt-lifecycle-viewmodel",
        type = ModuleType.MODULE
    )

    val all = listOf(
        navigationFragment,
        navigationCompose,
        navigationWork,
        lifecycleViewModel,
    )
}