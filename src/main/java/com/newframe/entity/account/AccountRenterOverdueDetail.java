package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * <p>
 * </p>
 *
 * @author wangdong
 * @since 2018-08-29
 */
@Data
@Entity
public class AccountRenterOverdueDetail {
    /**
     * id
     */
    @Id
    private Long id;

    /**
     * uid
     */
    private Long uid;
    /**
     * 平台自己的订单ID
     */
    private Long orderId;
    /**
     * 关联订单的ID
     */
    private Long associatedOrderId;

    /**
     * 投资的方式。1:融资购机，2:租机
     */
    private Integer investType;

    /**
     * 投资金额
     */
    private BigDecimal investAccount;

    /**
     * 投资期限
     */
    private Integer investMonth;

    /**
     * 保证金垫付金额
     */
    private BigDecimal cashDepositAccount;

    /**
     * 还款方式。1:押金贷，2:按月
     */
    private Integer payType;

    /**
     * 已付金额
     */
    private BigDecimal payedAccount;

    /**
     * 未付金额
     */
    private BigDecimal unpayedAccount;

    /**
     * 逾期天数
     */
    private Integer overdueDays;

    /**
     * 订单状态。1:正常，2:冻结
     */
    private Integer orderStatus;
    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;
}
