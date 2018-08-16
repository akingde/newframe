package com.newframe.services.userbase;

import com.newframe.entity.user.UserPwd;

/**
 *
 *  用户密码
 *
 * This class corresponds to the database table user_pwd
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
public interface UserPwdService {

    /**
     * 根据uid 查询用户的密码
     * @param uid
     * @return
     */
    UserPwd findByUid(Long uid);

    /**
     * 根据uid修改用户的密码
     * @param userPwd
     * @return
     */
    int updateByUid(UserPwd userPwd);

    /**
     *  添加
     * @param userPwd
     * @return
     */
    UserPwd insert(UserPwd userPwd);
}