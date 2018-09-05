package com.newframe.dto.order.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.08.31 16:37
 */
@Data
public class SupplierInfoDTO {
    private Long supplierId;
    private String supplierName;
    // todo 计算融资金额
    private BigDecimal financingAmount = new BigDecimal(4999);
}
