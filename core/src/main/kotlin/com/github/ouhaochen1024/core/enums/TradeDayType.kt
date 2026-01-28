package com.github.ouhaochen1024.core.enums

import org.babyfish.jimmer.sql.EnumItem

/**
 * 股票交易日类型枚举
 * 0-T+0 1-非T+0
 */
enum class TradeDayType {
    @EnumItem(name = "0")
    T_PLUS_ZERO,
    @EnumItem(name = "1")
    NON_T_PLUS_ZERO
}