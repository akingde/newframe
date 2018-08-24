package com.newframe.dto.order.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 资金方放款数据封装
 * @author kfm
 */
@Data
public class LoanDTO {
    private Long orderId;
    /**放款模式，1：贷款*/
    private Integer loanModel;
    private BigDecimal loanAmount;
    /**放款渠道，1：线上放款，2：线下放款*/
    private Integer loanChannel;
}
