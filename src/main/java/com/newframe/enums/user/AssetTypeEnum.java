package com.newframe.enums.user;

/**
 * @author WangBin
 */
public enum AssetTypeEnum {

    RECHARGE(1),
    DRAW(2);

    private Integer type;

    AssetTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
