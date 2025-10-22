package io.github.chengzis.plugin.starter

import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import io.github.chengzis.plugin.base.BasePlugin
import io.github.chengzis.plugin.base.ConfigUtil
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

@Suppress("UNUSED", "UNCHECKED_CAST")
class StarterPlugin : BasePlugin() {

    override fun applyRootProject(project: Project) {
        with(project) {
            val config = StartupConfig.fromConfig(ConfigUtil.loadConfig(this))
            println("project = $name plugin = startup $config")
            checkNotNull(config) { "startup config is null" }
            config.plugins?.forEach { plugin ->
                val pkg = "io.github.chengzis.plugin.$plugin"
                val className = plugin.uppercaseFirstChar() + "Plugin"

                val clazz = Class.forName("$pkg.$className") as Class<out Plugin<*>>
                project.plugins.apply(clazz)
            }
        }
    }

    override fun applyApplication(
        project: Project,
        application: ApplicationAndroidComponentsExtension
    ) {
        applyStater(project)
    }

    override fun applyLibrary(
        project: Project,
        library: LibraryAndroidComponentsExtension
    ) {
        applyStater(project)
    }

    /**
     * 应用项目状态的方法
     * 该方法用于对传入的Project对象进行状态设置或处理
     *
     * @param project 需要应用状态的项目对象，类型为Project
     * 这是一个私有方法，只能在当前类内部被调用
     */
    private fun applyStater(project: Project) {
        with(project) {
            val config = StartupConfig.fromConfig(ConfigUtil.loadConfig(this))
            println("project = $name plugin = startup $config")
            if (config == null) {
                return@with
            }
            config.plugins?.forEach { plugin ->
                pluginManager.apply("io.github.chengzis.plugin.$plugin")
            }
        }
    }
}