package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * <p>
 * 出租方逾期资产
 * </p>
 *
 * @author zww
 * @since 2018-08-29
 */
@Data
@Entity
public class AccountLessorOverdueAsset {

    /**
     * id
     */
    @Id
    private Long id;

    private Long uid;
    /**
     * 投资时间
     */
    private Long investTime;
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
     * 投资方式（1：融资购机，2：租机）
     */
    private Integer investWay;
    /**
     * 投资金额(融资购机没有此项)
     */
    private BigDecimal investAmount;
    /**
     * 投资期限
     */
    private BigDecimal investDeadline;
    /**
     * 还款方式（1：押金贷，2：按月支付）
     */
    private Integer repayWay;
    /**
     * 待还金额
     */
    private BigDecimal dueAmount;
    /**
     * 已还金额
     */
    private BigDecimal repayAmount;
    /**
     * 应还时间
     */
    private Long returnTime;
    /**
     * 实际还款时间
     */
    private Long actualReturnTime;
    /**
     * 逾期天数
     */
    private Integer overdueDay;
    /**
     * 逾期状态（1：逾期，2：催收成功，3：催收中，4：坏账）
     */
    private Integer overdueStatus;
    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;


    /**
     * 逾期资产合计
     */
    private Double totalOverdueAmount;
    /**
     * 逾期笔数
     */
    private Integer overdueNumber;
    /**
     * 逾期率
     */
    private Double overdueRate;


    /**
     * 已还金额
     */
    private BigDecimal payedAmount;
    /**
     * 待还金额
     */
    private BigDecimal unpayAmount;

    /**
     * 本月应收总额
     */
    private Double monthPayableAmount;

    private Integer orderStatus;

}
