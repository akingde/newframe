package com.newframe.enums.user;

/**
 * @author WangBin
 */
public enum AssetStatusEnum {

    CHECKING(1),//审核中
    BANK_PROCESSING(2),//银行处理中
    BANK_SUCC(3),//银行成功
    CHECK_ERROR(4),//审核失败
    BANK_ERROR(5),//银行处理失败
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
