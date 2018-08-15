package com.newframe.dto.user.response;

import lombok.Data;

import java.util.List;

/**
 * 用户登陆后的信息返回
 * @author WangBin
 */
@Data
public class UserBaseInfoDTO {

    private Long uid;
    private String token;
    private Integer status;
    private List<Role> role;

    @Data
    public static class Role{
        public Integer roleId;
        public Boolean result;
    }
}
