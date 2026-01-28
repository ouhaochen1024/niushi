package com.github.ouhaochen1024.core.enums

import org.babyfish.jimmer.sql.EnumItem

/**
 * 股票交易类型枚举
 * 0-股票 1-指数 2-etf 3-可转债
 */
enum class TradeType {
    @EnumItem(name = "0")
    STOCK,
    @EnumItem(name = "1")
    INDEX,
    @EnumItem(name = "2")
    ETF,
    @EnumItem(name = "3")
    CONVERTIBLE_BOND
}