package com.github.ouhaochen1024.job

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(properties = ["xxl.job.executor.enabled=false"])
class JobApplicationTests {

    @Test
    fun contextLoads() {
    }

}
