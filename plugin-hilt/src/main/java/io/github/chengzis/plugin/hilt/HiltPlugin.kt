package io.github.chengzis.plugin.hilt

import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import io.github.chengzis.plugin.base.BasePlugin
import io.github.chengzis.plugin.base.ConfigUtil
import io.github.chengzis.plugin.base.addDependency
import io.github.chengzis.plugin.base.replaceVersionWhenEmpty
import org.gradle.api.Project
import org.gradle.kotlin.dsl.buildscript

@Suppress("UNUSED")
class HiltPlugin : BasePlugin() {

    override fun applyRootProject(project: Project) {
        with(project) {
            val config = HiltConfig.fromConfig(ConfigUtil.loadConfig(this))
            println("project = $name plugin = hilt $config")
            checkNotNull(config) { "hilt config is null" }
            allprojects {
                buildscript {
                    dependencies.classpath("com.google.dagger:hilt-android-gradle-plugin:${config.version}")
                }
            }
        }
    }

    override fun applyApplication(
        project: Project,
        application: ApplicationAndroidComponentsExtension
    ) {
        val config = HiltConfig.fromConfig(ConfigUtil.loadConfig(project))
        if (config != null) {
            addDependencies(project, config)
        }
    }

    override fun applyLibrary(
        project: Project,
        library: LibraryAndroidComponentsExtension
    ) {
        val config = HiltConfig.fromConfig(ConfigUtil.loadConfig(project))
        if (config != null) {
            addDependencies(project, config)
        }
    }

    private fun addDependencies(project: Project, config: HiltConfig) {
        with(project) {
            println("project = ${project.name} plugin = hilt $config")
            pluginManager.apply("com.google.dagger.hilt.android")

            addDependency(HiltModule.runtime.replaceVersionWhenEmpty(config.version))
            addDependency(HiltModule.compiler.replaceVersionWhenEmpty(config.version))
            config.extLibs
                ?.mapNotNull { extLib ->
                    HiltModule.all.firstOrNull {
                        it.artifactId == extLib
                    }
                }
                ?.forEach {
                    addDependency(
                        it.replaceVersionWhenEmpty(
                            config.extLibsVersion ?: config.version
                        )
                    )
                }
        }
    }

}