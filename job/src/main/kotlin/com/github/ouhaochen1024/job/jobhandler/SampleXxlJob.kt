package com.github.ouhaochen1024.job.jobhandler

import com.xxl.job.core.context.XxlJobHelper
import com.xxl.job.core.handler.annotation.XxlJob
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class SampleXxlJob {
    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    @Throws(Exception::class)
    fun demoJobHandler() {
        XxlJobHelper.log("XXL-JOB, Hello World.")
        // default success
    }
}