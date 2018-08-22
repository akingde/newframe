package com.newframe.services.userbase;

import com.newframe.entity.user.UserAppToken;

/**

 *  app用户的token
 *
 *  This class corresponds to the database table user_app_token
 *
 *  @author WangBin
 *  @mbggenerated do_not_delete_during_merge
 *
 */
public interface UserAppTokenService {

    /**
     * 根据uid查询用户的apptoken
     * @param uid
     * @return
     */
    UserAppToken findOne(Long uid);

    /**
     * 根据uid更新token
     * @param uid
     * @param token
     * @return
     */
    int updateByUid(Long uid, String token);

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
    UserAppToken insert(Long uid);
}
