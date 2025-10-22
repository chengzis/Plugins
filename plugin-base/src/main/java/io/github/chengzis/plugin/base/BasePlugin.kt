package io.github.chengzis.plugin.base

import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class BasePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        if (target == target.rootProject) {
            applyRootProject(target)
        } else {
            target.extensions.findByType(ApplicationAndroidComponentsExtension::class.java)
                ?.let {
                    applyApplication(target, it)
                }
            target.extensions.findByType(LibraryAndroidComponentsExtension::class.java)
                ?.let {
                    applyLibrary(target, it)
                }
        }
    }

    protected abstract fun applyRootProject(project: Project)


    protected abstract fun applyApplication(
        project: Project,
        application: ApplicationAndroidComponentsExtension
    )

    protected abstract fun applyLibrary(
        project: Project,
        library: LibraryAndroidComponentsExtension
    )
}