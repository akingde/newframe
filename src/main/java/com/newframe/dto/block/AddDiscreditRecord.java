package com.newframe.dto.block;

import lombok.Data;
import types.EzTransfer;

import java.math.BigDecimal;

/**
 * @author WangBin
 */
@Data
public class AddDiscreditRecord {
    private Long uid;
    private Integer roleId;
    private EzTransfer.EzDiscreditRecordType discreditRecordType;
    private BigDecimal badDebtsAmount;
}
