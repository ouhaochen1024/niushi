package com.github.ouhaochen1024.web

import org.babyfish.jimmer.client.EnableImplicitApi
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableImplicitApi
@SpringBootApplication
class WebApplication

private val log = LoggerFactory.getLogger(WebApplication::class.java)

fun main(args: Array<String>) {
    val applicationContext = runApplication<WebApplication>(*args)
    val environment = applicationContext.environment
    val serverPort = environment.getProperty("server.port")
    val contextPath = environment.getProperty("server.servlet.context-path") ?: ""
    val protocol = "http"
    log.info(
        "\n----------------------------------------------------------\n" +
                "Application '{}' is running! Access URLs:\n" +
                "API doc: \t{}://localhost:{}{}/openapi.html\n" +
                "Profile(s): \t{}\n----------------------------------------------------------",
        environment.getProperty("spring.application.name"),
        protocol,
        serverPort,
        contextPath,
        environment.activeProfiles.joinToString(", ")
    )
}
