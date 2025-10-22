package io.github.chengzis.plugin.ksp

import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import io.github.chengzis.plugin.base.BasePlugin
import io.github.chengzis.plugin.base.ConfigUtil
import io.github.chengzis.plugin.base.KotlinConfig
import org.gradle.api.Project
import org.gradle.kotlin.dsl.buildscript

@Suppress("UNUSED")
class KspPlugin : BasePlugin() {

    override fun applyRootProject(project: Project) {
        with(project) {
            val config = ConfigUtil.loadConfig(this)

            val kspConfig = KspConfig.fromConfig(config)
            val kspVersion = if (kspConfig == null) {
                val kotlinConfig = KotlinConfig.fromConfig(config)
                checkNotNull(kotlinConfig) { "kotlin config and ksp config is null" }
                kotlinVersion2KspVersion(kotlinConfig.version) ?: latestKspVersion
            } else {
                kspConfig.version
            }
            println("project = $name plugin = ksp version = $kspVersion")
            allprojects {
                buildscript {
                    dependencies.classpath(
                        group = "com.google.devtools.ksp",
                        name = "com.google.devtools.ksp.gradle.plugin",
                        version = kspVersion
                    )
                }
            }
        }
    }

    override fun applyApplication(
        project: Project,
        application: ApplicationAndroidComponentsExtension
    ) {

    }

    override fun applyLibrary(
        project: Project,
        library: LibraryAndroidComponentsExtension
    ) {
    }

}