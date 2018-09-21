package com.newframe.dto.block;

import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class SupplierDeliver extends ExpressInfo {

    private Long orderNum;
    private Long supplierUid;
}
