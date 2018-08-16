package com.newframe.enums.user;

/**
 * 角色状态表
 * @author WangBin
 */
public enum RoleEnum {

    NORMAL(1),      //正常  审核通过
    UNDER_REVIEW(2),    //审核中
    REVIEW_FAILURE(3),//审核失败
    FREEZE(4),  //冻结
    REVOKE(5);  //撤销申请

    private Integer roleStatue;

    public Integer getRoleStatue() {
        return roleStatue;
    }

    public void setRoleStatue(Integer roleStatue) {
        this.roleStatue = roleStatue;
    }

    RoleEnum(Integer roleStatue) {
        this.roleStatue = roleStatue;
    }
}
