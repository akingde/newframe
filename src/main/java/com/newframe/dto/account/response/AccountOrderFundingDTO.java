package com.newframe.dto.account.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 资金方账户表
 * </p>
 *
 * @author zww
 * @since 2018-08-29
 */
@Data
public class AccountOrderFundingDTO {
    /**
     * 创建时间
     */
    private Integer ctime;
    /**
     * 更新时间
     */
    private Integer utime;
    /**
     * 订单的ID
     */
    private Long orderId;
    /**
     * 第几期
     */
    private Integer numberOfPeriods;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 订单的状态。1:正常，2:逾期
     */
    private Integer orderStatus;
    /**
     * 是否扣款。1:扣款，2:未扣款
     */
    private Integer withhold;
}
