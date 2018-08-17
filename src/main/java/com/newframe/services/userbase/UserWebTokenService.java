package com.newframe.services.userbase;

import com.newframe.entity.user.UserWebToken;

/**
 *
 *  用户pc的token
 *
 * This class corresponds to the database table user_web_token
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
public interface UserWebTokenService {

    /**
     * 根据uid查询用户的webtoken
     * @param uid
     * @return
     */
    UserWebToken findOne(Long uid);

    /**
     * 根据uid更新token
     * @param userWebToken
     * @return
     */
    int updateByUid(UserWebToken userWebToken);

    /**
     * 根据uid删除记录
     * @param uid
     * @return
     */
    void deleteByUid(Long uid);

    /**
     * 插入用户的token记录
     * @param uid
     * @return
     */
    UserWebToken insert(Long uid);
}