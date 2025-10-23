plugins {
    `kotlin-dsl`
    `maven-publish`
}

apply(from = rootProject.file("gradle/library.gradle"))
apply(from = rootProject.file("gradle/publish.gradle"))


// 依赖配置
dependencies {
    implementation("org.yaml:snakeyaml:2.2")
}

publishing {
    publications {
        create<MavenPublication>("pluginMaven") {
            groupId = property("project.groupId").toString()
            artifactId = property("project.artifactId").toString()
            version = property("project.version").toString()
        }
    }
}