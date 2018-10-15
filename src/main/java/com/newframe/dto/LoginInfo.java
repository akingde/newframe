package com.newframe.dto;

import com.newframe.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 【新生成的用户】
 *
 * @author wangdong  11/26/16.
 */
@Data
public class LoginInfo {

    private Long uid;

    private String token;

    private User user;

    public LoginInfo(){

    }

    public LoginInfo(Long uid, String token) {
        this.uid = uid;
        this.token = token;
    }
}
