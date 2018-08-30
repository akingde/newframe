package com.newframe.dto.user.response;

import com.newframe.entity.user.UserRole;
import com.newframe.enums.RoleEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 用户登陆后的信息返回
 * @author WangBin
 */
@Data
public class UserBaseInfoDTO {

    private Long uid;
    private String token;
    private String phoneNumber;
    private List<Role> role;

    public UserBaseInfoDTO() {
    }

    public UserBaseInfoDTO(Long uid, String token, String phoneNumber) {
        this.uid = uid;
        this.token = token;
        this.phoneNumber = phoneNumber;
        List<Role> roles = new ArrayList();
        for(RoleEnum roleEnum : RoleEnum.values()){
            Role role = new Role();
            role.setRoleId(roleEnum.getRoleId());
            role.setResult(false);
            roles.add(role);
        }
        this.role = roles;
    }

    public UserBaseInfoDTO(Long uid, String token, String phoneNumber, List<UserRole> userRoles) {
        this.uid = uid;
        this.token = token;
        this.phoneNumber = phoneNumber;
        List<Role> roles = new ArrayList();
        List<Integer> roleList = RoleEnum.roleList();
        List<Integer> list = userRoles.stream().map(UserRole::getRoleId).collect(toList());
        for (Integer roleId : roleList) {
            Role role = new Role();
            role.setRoleId(roleId);
            role.setResult(list.contains(roleId));
            roles.add(role);
        }
        this.role = roles;
    }

    @Data
    public static class Role{
        public Integer roleId;
        public Boolean result;
    }
}
