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
public class AccountRenterOverdueAsset {
    /**
     * uid
     */
    @Id
    private Long uid;
    /**
     * 逾期金额合计
     */
    private BigDecimal totalOverdueAccount;
    /**
     * 逾期笔数
     */
    private Integer overdueNumber;
    /**
     * 逾期率
     */
    private BigDecimal overdueRate;
    /**
     * 本月应收额
     */
    private BigDecimal monthReceivable;

    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;


    public void setAccountRenterOverdueAsset(Long uid, BigDecimal totalOverdueAccount, Integer overdueNumber, BigDecimal overdueRate, BigDecimal monthReceivable) {
        this.uid = uid;
        this.totalOverdueAccount = totalOverdueAccount;
        this.overdueNumber = overdueNumber;
        this.overdueRate = overdueRate;
        this.monthReceivable = monthReceivable;
    }
}
