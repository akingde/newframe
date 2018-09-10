package com.newframe.dto.order.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.08.27 19:24
 */
@Data
public class OrderSupplierDTO {
    private Long funderId;
    private Long orderId;
    private String consumerName;
    private String consumerPhone;
    private Long consumerUid;
    private String productName;
    private Integer productStorage;
    private Integer rentDeadlineDay;
    private Integer orderStatus;
    private String productBrand;
    private String productColor;
    private Integer loanTime;
    private Long renterId;
    private String renterName;
    private String consumerAddress;
    private String deliverCompany;
    private String deliverCode;
    private Long deliverTime;
    private Integer productRandomMemory;
    private Integer machineNumber;
    private String renterPhone;


    private Integer rentDeadlineMonth;
    private BigDecimal monthlyPayment;
    private BigDecimal downPayment;
    private BigDecimal accidentBenefit;

    private Integer deleteStatus;
    private Integer utime;
    /** 用户坏账次数*/
    private Integer consumerBedDebtTimes;
    private String serialNumber;
    private Integer orderTime;
    private Integer consumerOrderTime;


}
