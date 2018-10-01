package com.newframe.enums.order;

/**
 * @author kfm
 * @date 2018.10.01 15:23
 */
public enum OrderAssignStatusEnum {
    // 审核中
    AUDIT(0),
    // 审核未通过
    NO_PASS(2),
    // 审核通过
    PASS(1)
    ;
    private Integer code;
    OrderAssignStatusEnum(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
