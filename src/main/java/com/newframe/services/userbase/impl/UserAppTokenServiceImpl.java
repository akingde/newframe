package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserAppToken;
import com.newframe.repositories.dataMaster.user.UserAppTokenMaster;
import com.newframe.repositories.dataQuery.user.UserAppTokenQuery;
import com.newframe.repositories.dataSlave.user.UserAppTokenSlave;
import com.newframe.services.userbase.UserAppTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        UserAppTokenQuery query = new UserAppTokenQuery();
        query.setUid(uid);
        return userAppTokenSlave.findOne(query);
    }

    /**
     * 根据uid更新token
     *
     * @param userAppToken
     * @return
     */
    @Override
    public int updateByUid(UserAppToken userAppToken) {
        if(userAppToken == null) {
            return 0;
        }
        UserAppTokenQuery query = new UserAppTokenQuery();
        query.setUid(userAppToken.getUid());
        String[] array = new String[]{"token"};
        return userAppTokenMaster.update(userAppToken, query, array);
    }

    /**
     * 根据uid删除记录
     *
     * @param uid
     * @return
     */
    @Override
    public void deleteByUid(Long uid) {
        userAppTokenMaster.deleteById(uid);
    }

    /**
     * 插入用户的token记录
     *
     * @param uid
     * @return
     */
    @Override
    public UserAppToken insert(Long uid) {
        UserAppToken userAppToken = new UserAppToken();
        userAppToken.setUid(uid);
        userAppToken.setToken("");
        return userAppTokenMaster.save(userAppToken);
    }
}
