plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
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

// 配置发布
publishing {

    publications {
        create<MavenPublication>("pluginMaven") {
            groupId = property("project.groupId").toString()
            artifactId = property("project.artifactId").toString()
            version = property("project.version").toString()
        }
    }
}


// 依赖配置
dependencies {
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
    implementation("com.android.tools.build:gradle:8.1.0") // 如果需要与Android构建交互

    implementation("org.yaml:snakeyaml:2.2")
}