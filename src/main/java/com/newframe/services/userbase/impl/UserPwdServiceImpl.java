package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserPwd;
import com.newframe.services.userbase.UserPwdService;
import org.springframework.stereotype.Service;

/**
 *
 *  用户密码
 *
 * This class corresponds to the database table user_pwd
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
@Service
public class UserPwdServiceImpl implements UserPwdService {

    /**
     * 根据uid 查询用户的密码
     *
     * @param uid
     * @return
     */
    @Override
    public UserPwd findByUid(Long uid) {
        return null;
    }

    /**
     * 根据uid修改用户的密码
     *
     * @param userPwd
     * @return
     */
    @Override
    public int updateByUid(UserPwd userPwd) {
        return 0;
    }

    /**
     * 添加
     *
     * @param userPwd
     * @return
     */
    @Override
    public UserPwd insert(UserPwd userPwd) {
        return null;
    }
}