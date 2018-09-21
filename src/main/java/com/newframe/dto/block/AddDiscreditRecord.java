package com.newframe.dto.block;

import lombok.Data;
import types.EzTransfer;

import java.math.BigDecimal;

/**
 * 添加失信纪录
 * @author WangBin
 */
@Data
public class AddDiscreditRecord {
    /**
     * 用户id
     */
    private Long uid;
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 逾期类型  DRT_OVERDUE 逾期;  DRT_BAD_DEBTS 坏账
     */
    private EzTransfer.EzDiscreditRecordType discreditRecordType;
    /**
     * 坏账金额   逾期传 0
     */
    private BigDecimal badDebtsAmount ;
}
