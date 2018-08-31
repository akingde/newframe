/**
 * Copyright 2018 bejson.com
 */
package com.newframe.dto.account.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Auto-generated: 2018-08-30 18:20:25
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class AccountLessorMatterAssetListDTO {

    private long ctime;
    private long utime;
    /**
     * 关联订单的Id
     */
    private Long orderId;
    /**
     * 产品品牌
     */
    private String productModel;
    /**
     * 产品型号
     */
    private String productBrand;
    /**
     * 产品的颜色
     */
    private String productColour;
    /**
     * 产品的物理内存
     */
    private String productStorage;
    /**
     * 产品的运行内存
     */
    private String productMemory;
    /**
     * 订单的状态。1:正常，2:逾期，3:逾期未催收，4:催收中，5:催收已还机
     */
    private int orderStatus;
    /**
     * 租赁商的ID
     */
    private long renterId;
    /**
     * 租赁商的名字
     */
    private String renterName;
    /**
     * 用户ID
     */
    private long userId;
    /**
     * 用户的名字
     */
    private String userName;
    /**
     * 发货时间
     */
    private long deliverTime;
    /**
     * 购买价款
     */
    private BigDecimal purchaseAmount;
    /**
     * 租期
     */
    private BigDecimal rentMonth;
    /**
     * 租金总额
     */
    private BigDecimal totalRentAmount;

}