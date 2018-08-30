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
import java.util.UUID;

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
        //findOne，现在更新了，适用于根据唯一主键之间查，底层执行的是findByID
        //其余均有可能有多个值，请使用findAll()，以免数据库存在多个值查询抛错
        return userAppTokenSlave.findOne(uid);
    }

    /**
     * 根据uid更新token
     *
     * @param uid
     * @return
     */
    @Override
    public int updateByUid(Long uid, String token) {
        if(uid == null) {
            return 0;
        }
        UserAppToken userAppToken = new UserAppToken();
        userAppToken.setToken(token);
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
    public UserAppToken insert(Long uid, String token) {
        UserAppToken userAppToken = new UserAppToken();
        userAppToken.setUid(uid);
        userAppToken.setToken(token);
        return userAppTokenMaster.save(userAppToken);
    }
}
