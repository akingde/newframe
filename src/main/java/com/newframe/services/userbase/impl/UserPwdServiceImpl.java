package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserPwd;
import com.newframe.repositories.dataMaster.user.UserPwdMaster;
import com.newframe.repositories.dataQuery.user.UserPwdQuery;
import com.newframe.repositories.dataSlave.user.UserPwdSlave;
import com.newframe.services.userbase.UserPwdService;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private UserPwdMaster userPwdMaster;
    @Autowired
    private UserPwdSlave userPwdSlave;

    /**
     * 根据uid 查询用户的密码
     *
     * @param uid
     * @return
     */
    @Override
    public UserPwd findByUid(Long uid) {
        if (null == uid){
            return null;
        }
        return userPwdSlave.findOne(uid);
    }

    /**
     * 根据uid修改用户的密码
     *
     * @param userPwd
     * @return
     */
    @Override
    public int updateByUid(UserPwd userPwd) {
        if (userPwd == null){
            return 0;
        }
        UserPwdQuery query = new UserPwdQuery();
        query.setUid(userPwd.getUid());
        List<String> updateFields = new ArrayList();
        if (userPwd.getLoginPwd() != null){
            updateFields.add("loginPwd");
        }
        if(userPwd.getPayPwd() != null){
            updateFields.add("payPwd");
        }
        String[] array = new String[updateFields.size()];
        updateFields.toArray(array);
        return userPwdMaster.update(userPwd, query, array);
    }

    /**
     * 添加
     *
     * @param userPwd
     * @return
     */
    @Override
    public UserPwd insert(UserPwd userPwd) {
        if(userPwd == null)
            return null;
        return userPwdMaster.save(userPwd);
    }

    /**
     * 删除
     *
     * @param uid
     */
    @Override
    public void delete(Long uid) {
        userPwdMaster.deleteById(uid);
    }
}