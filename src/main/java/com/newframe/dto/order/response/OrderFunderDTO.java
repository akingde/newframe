package com.newframe.dto.order.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kfm
 * 资金方订单查询dto
 */
@Data
public class OrderFunderDTO {
    private Long uid;
    private Long orderId;
    private Integer orderTime;
    private String consumerName;
    private String consumerPhone;
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
    private String renterName;
    private Integer renterId;
    private Integer utime;
}
