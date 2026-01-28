dependencies {
    api("cn.hutool.v7:hutool-core:7.0.0-M4")
    api("com.alibaba.fastjson2:fastjson2:2.0.60")
    api("com.alibaba.fastjson2:fastjson2-kotlin:2.0.60")
}

tasks {
    named<Jar>("jar") {
        enabled = true
        archiveClassifier = ""
    }
}