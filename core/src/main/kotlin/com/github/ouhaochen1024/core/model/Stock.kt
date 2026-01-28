package com.github.ouhaochen1024.core.model

import com.github.ouhaochen1024.core.enums.*
import org.babyfish.jimmer.sql.*
import java.time.LocalDateTime

@Entity
@Table(name = "stock")
interface Stock {

    /**
     * 股票的主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 股票编码
     */
    val code: String

    /**
     * 股票名称
     */
    val name: String

    /**
     * 股票的交易所标识 0-深圳 1-上海 2-北京
     */
    val exchange: Exchange

    /**
     * 股票完整编码
     */
    val fullCode: String

    /**
     * 股票上市日期
     */
    val listingDate: LocalDateTime?

    /**
     * 股票交易类型 0-股票 1-指数 2-etf 3-可转债
     */
    val tradeType: TradeType

    /**
     * 股票交易天类型 0-T+0 1-非T+0
     */
    val tradeDayType: TradeDayType

    /**
     * 股票地区编码
     */
    val areaCode: String?

    /**
     * 股票地区名称
     */
    val areaName: String?

    /**
     * 未启用标识 0-启用 1-未启用
     */
    val disableFlag: DisableFlag

    /**
     * 删除标识 0-未删除 1-删除
     */
    val deleteFlag: DeleteFlag

    /**
     * 创建时间
     */
    val createTime: LocalDateTime

    /**
     * 更新时间
     */
    val updateTime: LocalDateTime
}
