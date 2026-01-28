import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.google.devtools.ksp")
}

val jimmerVersion: String by rootProject.extra

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}


configurations {
    configureEach {
        exclude("ch.qos.logback", "logback-classic")
        exclude("org.apache.logging.log4j", "log4j-to-slf4j")
    }
}

dependencies {
    implementation(project(":common"))

    api("org.springframework.boot:spring-boot-starter-jdbc")
    api("org.springframework.boot:spring-boot-starter-data-redis")
    api("org.springframework.boot:spring-boot-starter-aop")
    api("org.springframework.boot:spring-boot-starter-log4j2")
    api("org.springframework.boot:spring-boot-starter-mail")

    api("com.fasterxml.jackson.module:jackson-module-kotlin")

    api("org.postgresql:postgresql")
    api("com.zaxxer:HikariCP:7.0.2")

    api("org.babyfish.jimmer:jimmer-spring-boot-starter:${jimmerVersion}")
    api("org.babyfish.jimmer:jimmer-client-swagger:${jimmerVersion}")
    ksp("org.babyfish.jimmer:jimmer-ksp:${jimmerVersion}")
}

tasks {
    named<Jar>("jar") {
        enabled = true
        archiveClassifier = ""
    }
    named<BootJar>("bootJar") {
        enabled = false
    }
}