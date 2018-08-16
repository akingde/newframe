package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserWebToken;
import com.newframe.services.userbase.UserWebTokenService;
import org.springframework.stereotype.Service;

/**
 *
 *  用户pc的token
 *
 * This class corresponds to the database table user_web_token
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
@Service
public class UserWebTokenServiceImpl implements UserWebTokenService {

    /**
     * 根据uid查询用户的webtoken
     *
     * @param uid
     * @return
     */
    @Override
    public UserWebToken findOne(Long uid) {
        return null;
    }

    /**
     * 根据uid更新token
     *
     * @param userWebToken
     * @return
     */
    @Override
    public int updateByUid(UserWebToken userWebToken) {
        return 0;
    }

    /**
     * 根据uid删除记录
     *
     * @param uid
     * @return
     */
    @Override
    public int deleteByUid(Long uid) {
        return 0;
    }

    /**
     * 插入用户的token记录
     *
     * @param uid
     * @return
     */
    @Override
    public UserWebToken insert(Long uid) {
        return null;
    }
}