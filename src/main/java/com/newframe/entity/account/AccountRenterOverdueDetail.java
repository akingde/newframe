package com.newframe.entity.account;

import com.newframe.enums.account.OrderTypeEnum;
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
    private String associatedOrderId;
    /**
     * 关联订单的状态
     */
    private String associatedOrderStatus;

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
     * 订单状态。1:正常，2:逾期
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

    public void setAccountRenterOverdueDetail(Long uid,AccountRenterRentDetail accountRenterRentDetail){
        this.uid = uid;
        this.orderId = accountRenterRentDetail.getOrderId();
        this.associatedOrderId = accountRenterRentDetail.getAssociatedOrderId();
        this.associatedOrderStatus = accountRenterRentDetail.getAssociatedOrderStatus();
        this.investType = OrderTypeEnum.RENT.getCode();
        this.investAccount = accountRenterRentDetail.getTotalRentAccount();
        this.investMonth = accountRenterRentDetail.getMonthNumber();
        this.cashDepositAccount=BigDecimal.ZERO;
        this.payType=2;//还款方式，目前均是按月
        this.payedAccount = accountRenterRentDetail.getPayedAccount();
        this.unpayedAccount = accountRenterRentDetail.getUnpayedAccount();
        this.overdueDays = 0;
        this.orderStatus = accountRenterRentDetail.getOrderStatus();
    }

    public void setAccountRenterFinancing(Long uid,AccountRenterFinancing accountRenterFinancing){
        this.uid = uid;
        this.orderId = accountRenterFinancing.getOrderId();
        this.associatedOrderId = accountRenterFinancing.getAssociatedOrderId();
        this.associatedOrderStatus = accountRenterFinancing.getAssociatedOrderStatus();
        this.investType = OrderTypeEnum.FINANCING.getCode();
        this.investAccount = accountRenterFinancing.getFinancingAmount();
        this.investMonth = accountRenterFinancing.getFinancingMaturity();
        this.payType=2;//还款方式，目前均是按月
        this.payedAccount = accountRenterFinancing.getSettlePrincipalInterest().add(accountRenterFinancing.getSettleInterest());
        this.unpayedAccount = accountRenterFinancing.getUnsettlePrincipalInterest().add(accountRenterFinancing.getUnsettleInterest());
        this.overdueDays = 0;
        this.cashDepositAccount=BigDecimal.ZERO;
        this.orderStatus = accountRenterFinancing.getOrderStatus();
    }
}
