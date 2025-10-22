package io.github.chengzis.plugin.room

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import io.github.chengzis.plugin.base.BasePlugin
import io.github.chengzis.plugin.base.ConfigUtil
import io.github.chengzis.plugin.base.addDependency
import io.github.chengzis.plugin.base.replaceVersionWhenEmpty
import org.gradle.api.Project

@Suppress("UnstableApiUsage", "UNUSED")
class RoomPlugin : BasePlugin() {

    override fun applyRootProject(project: Project) {
    }

    override fun applyApplication(
        project: Project,
        application: ApplicationAndroidComponentsExtension
    ) {
        val config = RoomConfig.fromConfig(ConfigUtil.loadConfig(project))
        if (config == null) {
            return
        }
        application.finalizeDsl {
            println("project = ${project.name} application plugin room $config")
            addCompilerOptions(project, it, config)
            addDependencies(project, config)
        }
    }

    override fun applyLibrary(
        project: Project,
        library: LibraryAndroidComponentsExtension
    ) {
        val config = RoomConfig.fromConfig(ConfigUtil.loadConfig(project))
        if (config == null) {
            return
        }
        library.finalizeDsl {
            println("project = ${project.name} application plugin room $config")
            addCompilerOptions(project, it, config)
            addDependencies(project, config)
        }
    }

    private fun addDependencies(project: Project, config: RoomConfig) {
        with(project) {
            addDependency(RoomModule.runtime.replaceVersionWhenEmpty(config.version))
            addDependency(RoomModule.compiler.replaceVersionWhenEmpty(config.version))
            config.extLibs
                ?.mapNotNull { extLib -> RoomModule.all.firstOrNull { it.artifactId == extLib } }
                ?.forEach {
                    addDependency(it.replaceVersionWhenEmpty(config.version))
                }
        }
    }

    private fun addCompilerOptions(
        project: Project,
        commonExtension: CommonExtension<*, *, *, *, *>,
        config: RoomConfig,
    ) {
        val schemaLocation = config.schemaLocation
        if (schemaLocation == null) {
            return
        }

        with(commonExtension) {
            defaultConfig.javaCompileOptions
                .annotationProcessorOptions {
                    compilerArgumentProviders(
                        RoomSchemaArgProvider(project.mkdir(schemaLocation))
                    )

                    argument("room.incremental", (config.incremental ?: true).toString())
                    argument("room.generateKotlin", (config.generateKotlin ?: true).toString())
                }

            if (config.extLibs?.any { RoomModule.testing.artifactId == it } == true) {
                sourceSets.findByName("androidTest")
                    ?.assets?.srcDirs(schemaLocation)
            }
        }
    }
}