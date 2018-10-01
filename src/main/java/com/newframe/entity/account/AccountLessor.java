package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 出租方账户表
 * </p>
 *
 * @author zww
 * @since 2018-08-29
 */
@Data
@Entity
public class AccountLessor implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private BigDecimal payAmount;
    /**
     * 每月应收金额
     */
    private BigDecimal monthPayableAmount;
    /**
     * 账号状态,1:正常,2:冻结
     */
    private Integer accountStatus;
    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;

    /**
     * '逾期金额合计'
     */
    private BigDecimal overdueAmount;
    /**
     * '逾期笔数'
     */
    private Integer overdueCount;
    /**
     * '逾期率'
     */
    private Double overdueRate;
    /**
     * '累计租金收入'
     */
    private BigDecimal matterRentAmount;
    /**
     * '已收租金'
     */
    private BigDecimal matterRentPayed;
    /**
     * '待收租金'
     */
    private BigDecimal matterRentUnpayed;
}
