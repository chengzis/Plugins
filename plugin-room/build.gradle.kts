plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
    id("com.gradle.plugin-publish") version "2.0.0" // 可选，用于发布到Gradle插件门户
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withJavadocJar()
    withSourcesJar()
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}


val groupId = property("project.groupId").toString()
val artifactId = property("project.artifactId").toString()

group = "$groupId.$artifactId"
version = "1.0.0"

gradlePlugin {
    plugins {
        val name = property("project.name").toString()
        create(name) {
            id = group.toString()
            implementationClass = "$group.$name"
            displayName = name
            description = property("project.description").toString()
        }
    }
}

// 配置发布
publishing {
    repositories {
        // 发布到 Maven Local (测试用)
        mavenLocal()
    }
}

// 配置签名 (发布到Maven Central必需)
//signing {
//    val signingKey: String? by project
//    val signingPassword: String? by project
//    useInMemoryPgpKeys(signingKey, signingPassword)
//    sign(publishing.publications["pluginMaven"])
//}

// 依赖配置
dependencies {
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
    implementation("com.android.tools.build:gradle:8.1.0") // 如果需要与Android构建交互

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.assertj:assertj-core:3.24.2")

    api(
        group = property("project.groupId").toString(),
        name = "base",
        version = property("project.version").toString(),
    )
}
