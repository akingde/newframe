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
    private Role role;

    public UserBaseInfoDTO() {
    }

    public UserBaseInfoDTO(Long uid, String token, String phoneNumber) {
        this.uid = uid;
        this.token = token;
        this.phoneNumber = phoneNumber;
    }

    public UserBaseInfoDTO(Long uid, String token, String phoneNumber, Role role) {
        this.uid = uid;
        this.token = token;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    @Data
    public static class Role{
        public Integer roleId;
        public Boolean result;

        public Role(Integer roleId, Boolean result) {
            this.roleId = roleId;
            this.result = result;
        }
    }
}
