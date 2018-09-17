package com.newframe.enums.user;

/**
 * @author WangBin
 */
public enum AssetStatusEnum {

    CHECKING(1),
    BANK_PROCESSING(2),
    BANK_SUCC(3),
    CHECK_ERROR(4),
    BANK_ERROR(5),
    ;

    private Integer orderStatus;

    AssetStatusEnum(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
