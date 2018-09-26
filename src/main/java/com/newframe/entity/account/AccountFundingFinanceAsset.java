package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
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
@Entity
public class AccountFundingFinanceAsset {
    /**
     * id
     */
    @Id
    private Long id;
    /**
     * 资金方uid
     */
    private Long uid;
    /**
     * 关联订单id
     */
    private Long orderId;
    /**
     * 订单时间
     */
    private Long orderTime;
    /**
     * 关联订单的ID
     */
    private String associatedOrderId;
    /**
     * 关联订单状态
     */
    private String associatedOrderStatus;
    /**
     * 投资金额
     */
    private BigDecimal investAmount;
    /**
     * 投资期限
     */
    private Integer investDeadline;
    /**
     * 投资方式(1：融资购机)
     */
    private Integer investWay;
    /**
     * 收益率
     */
    private BigDecimal yieldRate;
    /**
     * 融资人-租赁商ID
     */
    private Long renterId;
    /**
     * 租赁商的名字
     */
    private String renterName;
    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;


    /**
     * 投资回报率
     */
    private BigDecimal investReturnRate;
    /**
     * 市场平均投资回报率
     */
    private BigDecimal averageInvestReturnRate;

    private Integer orderStatus;

}
