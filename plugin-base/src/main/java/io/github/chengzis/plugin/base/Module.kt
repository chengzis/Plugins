package io.github.chengzis.plugin.base


enum class ModuleType(val configurationName: String) {

    MODULE("implementation"),

    KSP_MODULE("ksp"),

    KAPT_MODULE("annotationProcessor"),

    TEST("testImplementation"),

    ANDROID_TEST("androidTestImplementation")

}

data class Module(
    val groupId: String,
    val artifactId: String,
    val type: ModuleType,
    val version: String? = null
)


/**
 * 当模块版本为空时，替换指定版本
 * @param version 要设置的版本号
 * @return 返回更新后的模块实例，如果原版本不为空则返回原模块
 */
fun Module.replaceVersionWhenEmpty(version: String): Module {
    // 检查当前模块的版本是否为空
    if (this.version == null) {
        // 如果版本为空，创建一个新模块实例并设置版本
        return copy(version = version)
    }
    // 如果版本不为空，直接返回原模块
    return this
}