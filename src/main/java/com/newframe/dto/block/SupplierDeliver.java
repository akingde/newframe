package com.newframe.dto.block;

import lombok.Data;

/**
 * 供应商发货
 * @author WangBin
 */
@Data
public class SupplierDeliver extends ExpressInfo {

    /**
     * 订单id
     */
    private Long orderNum;
    /**
     * 供应商uid
     */
    private Long supplierUid;
}
