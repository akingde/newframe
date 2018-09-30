package com.newframe.dto.order.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 出租方订单封装
 * @author kfm
 * @date 2018.08.29 15:16
 */
@Data
public class OrderHirerDTO {
    private Long uid;
    private Long orderId;
    private String consumerName;
    private String consumerPhone;
    private Long consumerUid;
    private String consumerIdentityNumber;
    private Integer consumerCreditScore;
    private String productName;
    private Integer productStorage;
    private Integer rentDeadlineMonth;
    private Integer rentDeadlineDay;
    private BigDecimal monthlyPayment;
    private BigDecimal downPayment;
    private BigDecimal accidentBenefit;
    private Integer orderStatus;
    private Integer consumerCreditLine;
    private String productBrand;
    private String productColor;
    private Integer orderTime;
    private Long renterId;
    private String renterName;
    private String renterPhone;
    private Integer productRandomMemory;
    private Integer machineNumber;
    private String consumerAddress;
    private Integer consumerOrderTime;
    private Integer consumerBedDebtTimes;
    private Integer platformCreditScore;
    private String receiverPhone;
    private String receiverName;
    private Integer patternPayment;
    private BigDecimal orderAmount;
}
