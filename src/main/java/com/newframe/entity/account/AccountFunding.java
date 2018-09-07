package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
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
@Entity
public class AccountFunding {
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
    private BigDecimal cashDeposit;
    /**
     * 待收金额
     */
    private BigDecimal dueAmount;
    /**
     * 本月应收
     */
    private BigDecimal monthPayableAmount;
    /**
     * 账号状态,1:正常,2:冻结
     */
    private Short accountStatus;
    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;
}
