package com.newframe.dto.block;

import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class FundSupplier {
    private Long orderNum;
    private Long funderUid;
    private Long supplierUid;
    private String fundVoucher;
    private Integer loanTime;
}
