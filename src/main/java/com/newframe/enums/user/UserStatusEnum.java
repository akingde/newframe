package com.newframe.enums.user;

/**
 * @author WangBin
 */
public enum UserStatusEnum {

    NORMAL(1),      //正常  审核通过
    FREEZE(2);  //冻结

    private Integer userStatus;

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    UserStatusEnum(Integer userStatus) {
        this.userStatus = userStatus;
    }
}
