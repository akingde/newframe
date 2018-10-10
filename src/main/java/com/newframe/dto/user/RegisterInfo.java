package com.newframe.dto.user;

import lombok.Data;

/**
 *
 * 用户登陆后将用户的Uid和Token返回给前端
 * @author wangdong
 */
@Data
public class RegisterInfo {

    private Long uid;

    private Boolean hasRegister;

    private Boolean hasLoginPwd;

}
