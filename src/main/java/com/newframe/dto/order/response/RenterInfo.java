package com.newframe.dto.order.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 租赁商信息封装
 * @author kfm
 * @date 2018.09.01 13:23
 */
@Data
public class RenterInfo {
    private long renterId;
    private String renterName;
    private int overdueTimes;
    private int badDeptTimes;
    private BigDecimal financingAmount;
    private BigDecimal overdueAmount;
    private String address;
    private String renterPhone;
}
