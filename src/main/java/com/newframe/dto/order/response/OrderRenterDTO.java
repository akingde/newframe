package com.newframe.dto.order.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kfm
 * 租赁商订单查询数据封装
 */
@Data
public class OrderRenterDTO {

    private Long orderId;
    private Integer orderTime;
    private String consumerName;
    private String consumerPhone;
    private Long consumerUid;
    private Long renterId;
    private String renterName;
    private String consumerIdentityNumber;
    private Integer consumerCreditScore;
    private String productName;
    private Integer productStorage;
    private Integer productRandomMemory;
    private Integer rentDeadlineMonth;
    private Integer rentDeadlineDay;
    private BigDecimal monthlyPayment;
    private BigDecimal downPayment;
    private BigDecimal accidentBenefit;
    private Integer consumerCreditLine;
    private String productBrand;
    private Integer orderStatus;
    private String productColor;
    private Integer deleteStatus;
    private Integer utime;
    private String consumerAddress;
}
