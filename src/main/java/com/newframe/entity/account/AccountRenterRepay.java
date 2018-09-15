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
public class AccountRenterRepay {
    /**
     * orderId
     */
    @Id
    private Long id;

    private Long orderId;
    /**
     * 第几期
     */
    private Integer numberPeriods;
    /**
     * 每期的金额
     */
    private BigDecimal orderAmount;


    /**
     * 订单的状态。1:正常，2:逾期
     */
    private Integer orderStatus;

    /**
     * 是否扣款。1:已扣款，2:未扣款
     */
    private Integer withhold;

    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;
//    private String partnerOrderId;
//    private Integer lastRepayTime;
//    private Integer actualRepayTime;
//    private Long payeeId;
}
