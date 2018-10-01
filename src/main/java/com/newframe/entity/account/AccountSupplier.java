package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 供应商账户表
 * </p>
 *
 * @author zww
 * @since 2018-08-31
 */
@Data
@Entity
public class AccountSupplier implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long uid;
    /**
     * 可用金额
     */
    private BigDecimal useableAmount;
    /**
     * 资产总额
     */
    private BigDecimal totalAsset;
    /**
     * 冻结资产
     */
    private BigDecimal frozenAsset;

    /**
     * 累计营收
     */
    private BigDecimal totalEarning;
    /**
     * 累计销售数量
     */
    private Integer saleNumber;
    /**
     * 待发货数量
     */
    private Integer deliverNumber;

    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;

}
