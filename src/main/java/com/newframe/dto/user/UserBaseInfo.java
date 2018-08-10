package com.newframe.dto.user;

import lombok.Data;

import java.util.List;

/**
 * 用户登陆后的信息返回
 * @author WangBin
 */
@Data
public class UserBaseInfo {

    private Long uid;
    private String token;
    private Integer status;
    private List<Integer> role;
}
