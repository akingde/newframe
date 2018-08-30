package com.newframe.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色枚举
 * @author WangBin
 */
public enum  RoleEnum {

    FUNDER(1),  //资金方
    HIRER(2),   //出租方
    SUPPLIER(3),//供应商
    FIRST_RENT_MERCHANT(4),//一级租赁商
    SECOND_RENT_MERCHANT(5);//二级租赁商

    private Integer roleId;

    RoleEnum(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public static List<Integer> roleList(){
        List<Integer> list = new ArrayList<>();
        for(RoleEnum roleEnum : RoleEnum.values()){
            list.add(roleEnum.getRoleId());
        }
        return list;
    }
}
