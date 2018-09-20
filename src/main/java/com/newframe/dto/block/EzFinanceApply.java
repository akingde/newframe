package com.newframe.dto.block;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author WangBin
 */
@Data
public class EzFinanceApply extends EzLesseeOrder{

    private Long orderNum;
    private Long merchantUid;
    private BigDecimal financingAmount;
    private Integer financingTerm;
    private Long funderUid;
    private Long supplierUid;
    private Integer applyTime;

}
