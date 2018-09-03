package com.newframe.dto.order.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.09.01 15:02
 */
@Data
public class RentInfo {
    private BigDecimal monthPayment;
    private Integer rentDeadline;
    private BigDecimal downPayment;
    private BigDecimal accidentBenefit;
    private Integer patternPayment;
    private Long lessorId;
    private String lessorName;
    private Integer rentTime;
    private BigDecimal paymentAmount;
}
