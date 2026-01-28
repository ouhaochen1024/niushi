import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    id("org.springframework.boot") version "3.5.9"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.google.devtools.ksp") version "2.3.4"
}

group = "com.github.ouhaochen1024"
version = "1.0.0"
description = "KotlinSpringBootStandard"
val jimmerVersion by extra { "0.9.120" }

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

allprojects {
    repositories {
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }
    tasks{
        withType<Test> {
            useJUnitPlatform()
        }
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }
    }
    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
        }
    }
    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
    }
}

tasks {
    named<Jar>("jar") {
        enabled = false
    }
    named<BootJar>("bootJar") {
        enabled = false
    }
}