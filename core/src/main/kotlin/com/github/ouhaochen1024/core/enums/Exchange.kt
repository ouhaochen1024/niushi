package com.github.ouhaochen1024.core.enums

import org.babyfish.jimmer.sql.EnumItem

/**
 * 股票交易所枚举
 * 0-深圳 1-上海 2-北京
 */
enum class Exchange {
    @EnumItem(name = "0")
    SHENZHEN,
    @EnumItem(name = "1")
    SHANGHAI,
    @EnumItem(name = "2")
    BEIJING
}