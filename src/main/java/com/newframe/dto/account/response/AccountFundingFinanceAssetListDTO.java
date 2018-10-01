package com.newframe.dto.account.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 资金方金融资产表
 * </p>
 *
 * @author zww
 * @since 2018-08-29
 */
@Data
public class AccountFundingFinanceAssetListDTO {
    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 关联订单的ID
     */
    private String associatedOrderId;
    /**
     * 关联订单状态
     */
    private String associatedOrderStatus;

    /**
     * 关联订单时间
     */
    private Long orderTime;

    /**
     * 投资方式(1：融资购机)
     */
    private Integer investWay;

    /**
     * 融资利率
     */
    private Double investInterestRate;

    /**
     * 投资金额
     */
    private BigDecimal investAmount;

    /**
     * 投资期限
     */
    private Integer investMonth;

    /**
     * 收益率
     */
    private BigDecimal earningsRate;
    /**
     * 融资人-租赁商ID
     */
    private Long renterId;
    /**
     * 租赁商的名字
     */
    private String renterName;
    /**
     * 订单的状态。1:正常
     */
    private Integer orderStatus;

}
