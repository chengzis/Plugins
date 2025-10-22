package io.github.chengzis.plugin.base

import org.gradle.api.Project

/**
 * 为项目添加依赖项的扩展函数
 * @param module 要添加的模块依赖对象，包含groupId、artifactId、version等信息
 */
fun Project.addDependency(module: Module) {
    // 首先检查并可能添加必要的插件
    maybeAddPlugin(module)
    // 使用with作用域函数，直接访问Module的属性
    with(module) {
        // 将依赖添加到项目的依赖集合中
        dependencies.add(
            // 依赖类型对应的配置名称
            type.configurationName,
            // 依赖的完整标识符，格式为"groupId:artifactId:version"
            "$groupId:$artifactId:$version"
        )
    }
}

/**
 * 为项目添加可能需要的插件
 * 这是一个扩展函数，用于根据模块类型决定是否添加特定的插件
 * @param module 需要检查的模块对象
 */
private fun Project.maybeAddPlugin(module: Module) {
    // 使用with函数简化对module对象的访问
    with(module) {
        // 根据模块类型决定添加哪种插件
        when (type) {
            // 如果是KSP模块，则添加KSP插件
            ModuleType.KSP_MODULE -> pluginManager.apply("com.google.devtools.ksp")
            // 如果是KAPT模块，则添加Kotlin KAPT插件
            ModuleType.KAPT_MODULE -> pluginManager.apply("kotlin-kapt")
            // 其他类型模块不执行任何操作
            else -> {}
        }
    }
}