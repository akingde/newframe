package com.newframe.enums;

import java.util.ArrayList;
import java.util.List;
import static com.newframe.enums.Contansts.*;

/**
 * 角色枚举
 * @author WangBin
 */
public enum  RoleEnum {



    FUNDER(funder, new Integer[]{funder, hirer}),  //资金方
    HIRER(hirer, new Integer[]{hirer}),   //出租方
    SUPPLIER(supplier, new Integer[]{supplier, hirer}),//供应商
    FIRST_RENT_MERCHANT(bigRent, new Integer[]{bigRent, supplier, hirer}),//一级租赁商
    SECOND_RENT_MERCHANT(smallRent, null);//二级租赁商



    private Integer roleId;
    private Integer[] roleIds;

    RoleEnum(Integer roleId, Integer[] roleIds) {
        this.roleId = roleId;
        this.roleIds = roleIds;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Integer[] roleIds) {
        this.roleIds = roleIds;
    }

    public static List<Integer> roleList(){
        List<Integer> list = new ArrayList<>();
        for(RoleEnum roleEnum : RoleEnum.values()){
            list.add(roleEnum.getRoleId());
        }
        return list;
    }

    public static Integer[] getRoleIdsByRoleId(Integer roleId){
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if(roleId.equals(roleEnum.getRoleId())){
                return roleEnum.getRoleIds();
            }
        }
        return null;
    }
}
