package com.github.ouhaochen1024.web.controller

import com.github.ouhaochen1024.core.model.Stock
import com.github.ouhaochen1024.core.model.dto.ComplexStockView
import com.github.ouhaochen1024.core.model.dto.SimpleStockView
import com.github.ouhaochen1024.core.model.name
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dto")
@Transactional
class StockController(
    private val sqlClient: KSqlClient
) {
    @GetMapping("/stock/simple")
    fun findSimpleBooks(
        @RequestParam(required = false) name: String?
    ): List<SimpleStockView> =
        sqlClient.executeQuery(Stock::class) {
            where(table.name `eq?` name)
            select(table.fetch(SimpleStockView::class))
        }

    @GetMapping("/stock/complex")
    fun findComplexBooks(
        @RequestParam(required = false) name: String?
    ): List<ComplexStockView> =
        sqlClient.executeQuery(Stock::class) {
            where(table.name `eq?` name)
            select(table.fetch(ComplexStockView::class))
        }
}