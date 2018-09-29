package com.newframe.dto.block;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 租赁商向资金方融资
 * @author WangBin
 */
@Data
public class FinanceApply extends LesseeOrder {

    /**
     * 订单id
     */
    private String orderNum;
    /**
     * 租赁商uid
     */
    private Long merchantUid;
    /**
     * 融资金额
     */
    private BigDecimal financingAmount;
    /**
     * 融资期限
     */
    private Integer financingTerm;
    /**
     * 资金方uid
     */
    private Long funderUid;
    /**
     * 供应商uid
     */
    private Long supplierUid;
    /**
     * 申请时间
     */
    private Integer applyTime;

}
