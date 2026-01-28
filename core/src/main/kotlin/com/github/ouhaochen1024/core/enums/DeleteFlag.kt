package com.github.ouhaochen1024.core.enums

import org.babyfish.jimmer.sql.EnumItem

/**
 * 删除状态枚举
 * 0-未删除 1-删除
 */
enum class DeleteFlag {
    @EnumItem(name = "0")
    NOT_DELETED,
    @EnumItem(name = "1")
    DELETED
}