/**
 * Copyright 2018 bejson.com
 */
package com.newframe.dto.account.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Auto-generated: 2018-08-31 15:33:39
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class AccountSupplierSellListDTO {
    private Integer ctime;
    private Integer utime;
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
    private String productColor;
    /**
     * 产品的物理内存
     */
    private Integer productStorage;
    /**
     * 产品的运行内存
     */
    private Integer productMemory;

    private BigDecimal totalAccount;

    /**
     * 订单的状态。1:正常，2:逾期，3:逾期未催收，4:催收中，5:催收已还机
     */
    private Integer orderStatus;
    /**
     * 租赁商的ID
     */
    private Long renterId;
    /**
     * 租赁商的名字
     */
    private String renterName;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户的名字
     */
    private String userName;
    /**
     * 发货时间
     */
    private Long deliverTime;
}