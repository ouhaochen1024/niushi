import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.google.devtools.ksp")
}

val jimmerVersion: String by rootProject.extra

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
    sourceSets.main {
        kotlin.srcDir(project(":core").projectDir.resolve("build/generated/ksp/main/kotlin"))
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
    implementation(project(":core"))

    implementation("com.xuxueli:xxl-job-core:3.3.2")

    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    ksp("org.babyfish.jimmer:jimmer-ksp:${jimmerVersion}")
}


tasks {
    named<Jar>("jar") {
        enabled = false
    }
    named<BootJar>("bootJar") {
        enabled = true
        archiveClassifier = ""
        mainClass.set("com.github.ouhaochen1024.job.JobApplication")
    }
}