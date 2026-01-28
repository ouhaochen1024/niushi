package com.github.ouhaochen1024.job.config

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val log = LoggerFactory.getLogger(XxlJobConfig::class.java)

@Configuration
class XxlJobConfig {

    @Value("\${xxl.job.admin.addresses}")
    private val adminAddresses: String? = null

    @Value("\${xxl.job.admin.accessToken}")
    private val accessToken: String? = null

    @Value("\${xxl.job.admin.timeout}")
    private val timeout = 3

    @Value("\${xxl.job.executor.enabled}")
    private val enabled: Boolean? = null

    @Value("\${xxl.job.executor.appname}")
    private val appname: String? = null

    @Value("\${xxl.job.executor.address}")
    private val address: String? = null

    @Value("\${xxl.job.executor.ip}")
    private val ip: String? = null

    @Value("\${xxl.job.executor.port}")
    private val port = 0

    @Value("\${xxl.job.executor.logpath}")
    private val logPath: String? = null

    @Value("\${xxl.job.executor.logretentiondays}")
    private val logRetentionDays = 0

    @Value("\${xxl.job.executor.excludedpackage}")
    private val excludedPackage: String? = null


    @Bean
    fun xxlJobExecutor(): XxlJobSpringExecutor {
        log.info(">>>>>>>>>>> xxl-job config init.")
        val xxlJobSpringExecutor = XxlJobSpringExecutor()
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses)
        xxlJobSpringExecutor.setAccessToken(accessToken)
        xxlJobSpringExecutor.setTimeout(timeout)
        xxlJobSpringExecutor.setEnabled(enabled)
        xxlJobSpringExecutor.setAppname(appname)
        xxlJobSpringExecutor.setAddress(address)
        xxlJobSpringExecutor.setIp(ip)
        xxlJobSpringExecutor.setPort(port)
        xxlJobSpringExecutor.setLogPath(logPath)
        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays)
        xxlJobSpringExecutor.setExcludedPackage(excludedPackage)
        return xxlJobSpringExecutor
    }

}