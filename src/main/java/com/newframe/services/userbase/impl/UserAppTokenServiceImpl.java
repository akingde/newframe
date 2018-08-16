package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserAppToken;
import com.newframe.repositories.dataMaster.user.UserAppTokenMaster;
import com.newframe.repositories.dataSlave.user.UserAppTokenSlave;
import com.newframe.services.userbase.UserAppTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**

 *  app用户的token
 *
 *  This class corresponds to the database table user_app_token
 *
 *  @author WangBin
 *  @mbggenerated do_not_delete_during_merge
 */
@Service
public class UserAppTokenServiceImpl implements UserAppTokenService {

    @Autowired
    private UserAppTokenMaster userAppTokenMaster;
    @Autowired
    private UserAppTokenSlave userAppTokenSlave;

    /**
     * 根据uid查询用户的apptoken
     *
     * @param uid
     * @return
     */
    @Override
    public UserAppToken findOne(Long uid) {
        return null;
    }

    /**
     * 根据uid更新token
     *
     * @param userAppToken
     * @return
     */
    @Override
    public int updateByUid(UserAppToken userAppToken) {
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
    public UserAppToken insert(Long uid) {
        return null;
    }
}
