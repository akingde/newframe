package com.newframe.dto.order.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.08.31 16:37
 */
@Data
public class LessorInfoDTO {
    private Long lessorId;
    private String lessorName;
    private Integer numberOfPeriods;
    private BigDecimal monthlyPayment;
    private BigDecimal fullRepayAmount;
    private BigDecimal accidentBenefit;
}
