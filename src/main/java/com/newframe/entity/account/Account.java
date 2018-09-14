package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * <p>
 * 商账户表
 * </p>
 *
 * @author wangdong
 * @since 2018-08-29
 */
@Data
@Entity
public class Account {
    /**
     * uid
     */
    @Id
    private Long uid;
    /**
     * 可用金额
     */
    private BigDecimal useableAmount;
    /**
     * 资产总额
     */
    private BigDecimal totalAssets;
    /**
     * 冻结资产
     */
    private BigDecimal frozenAssets;
    /**
     * 保证金
     */
    private BigDecimal marginBalance;
    /**
     * 保证金垫付金额
     */
    private BigDecimal marginAdvances;

    /**
     * 待收金额
     */
    private BigDecimal dueAmount;

    /**
     * 账号状态,1:正常,2:冻结
     */
    private Integer accountStatus;

    /**
     * 本月应收
     */
    private BigDecimal currentMonthPayment;

    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;
}
