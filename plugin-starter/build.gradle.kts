plugins {
    `kotlin-dsl`
}

apply(from = rootProject.file("gradle/plugin.library.gradle"))


// 配置签名 (发布到Maven Central必需)
//signing {
//    val signingKey: String? by project
//    val signingPassword: String? by project
//    useInMemoryPgpKeys(signingKey, signingPassword)
//    sign(publishing.publications["pluginMaven"])
//}

// 依赖配置
dependencies {
    val groupId = property("project.groupId").toString()
    rootProject
        .childProjects
        .map { it.value }
        .filter { it != project }
        .forEach {
            val artifactId = it.property("project.artifactId").toString()
            if (artifactId == "base") return@forEach
            api(
                group = "$groupId.$artifactId",
                name = "$groupId.$artifactId.gradle.plugin",
                version = it.property("project.version").toString(),
            )
        }

}
