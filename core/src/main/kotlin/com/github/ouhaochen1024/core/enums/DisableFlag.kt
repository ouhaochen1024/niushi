package com.github.ouhaochen1024.core.enums

import org.babyfish.jimmer.sql.EnumItem

/**
 * 启用状态枚举
 * 0-启用 1-未启用
 */
enum class DisableFlag {
    @EnumItem(name = "0")
    ENABLED,
    @EnumItem(name = "1")
    DISABLED
}