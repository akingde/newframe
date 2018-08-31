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
public class AccountRenterRentDetail {
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
     * 手机品牌
     */
    private String productBrand;
    /**
     * 手机型号
     */
    private String productModel;

    /**
     * 手机颜色
     */
    private String productColour;

    /**
     * 手机物理内存
     */
    private String productStorage;

    /**
     * 手机运行内存
     */
    private String productMemory;

    /**
     * 租金总额
     */
    private BigDecimal totalRentAccount;

    /**
     * 租期
     */
    private Integer monthNumber;

    /**
     * 已付租金
     */
    private BigDecimal payedAccount;

    /**
     * 未付租金
     */
    private BigDecimal unpayedAccount;

    /**
     * 关联订单的状态。1:正常，2:逾期，3:逾期未催收，4:催收中，5:催收已还机
     */
    private Integer orderStatus;

    /**
     * 还款状态。1:正常，2:已结清，3:逾期未催收，4:逾期已催收，5:已转让
     */
    private Integer payStatus;

    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;
}
