package com.newframe.dto.block;

import lombok.Data;

/**
 * 资金方放款给供应商
 * @author WangBin
 */
@Data
public class FundSupplier {
    /**
     * 订单id
     */
    private Long orderNum;
    /**
     * 资金方id
     */
    private Long funderUid;
    /**
     * 供应商uid
     */
    private Long supplierUid;
    /**
     * 线下放款 图片url  非线下放款不用传递
     */
    private String fundVoucher;
    /**
     * 放款时间
     */
    private Integer loanTime;
}
