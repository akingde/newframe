package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * <p>
 * 租赁商账户表
 * </p>
 *
 * @author wangdong
 * @since 2018-08-29
 */
@Data
@Entity
public class AccountRenterRent {
    /**
     * uid
     */
    @Id
    private Long id;

    /**
     * uid
     */
    private Long uid;
    /**
     * 平台订单id
     */
    private Long orderId;
    /**
     * 关联订单的ID
     */
    private Long relevanceOrderId;
    /**
     * 应收租金
     */
    private BigDecimal receivableAccount;
    /**
     * 已收租金
     */
    private BigDecimal receivedAccount;
    /**
     * 待收租金
     */
    private BigDecimal dueInAccount;

    /**
     * 订单状态,1:正常,2:冻结
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