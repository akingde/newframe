package com.newframe.services.user.impl;

import com.newframe.entity.user.User;
import com.newframe.entity.user.UserPwd;
import com.newframe.enums.User.PassWordTypeEnum;
import com.newframe.repositories.dataMaster.user.UserMaster;
import com.newframe.repositories.dataMaster.user.UserPwdMaster;
import com.newframe.repositories.dataQuery.user.UserPwdQuery;
import com.newframe.repositories.dataQuery.user.UserQuery;
import com.newframe.repositories.dataSlave.user.UserPwdSlave;
import com.newframe.repositories.dataSlave.user.UserSlave;
import com.newframe.services.http.OkHttpService;
import com.newframe.services.user.UserService;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:wangdong
 * @description:用户原子类的实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserSlave userSlave;

    @Autowired
    private IdGlobalGenerator idGen;

    @Autowired
    private UserMaster userMaster;

    @Autowired
    private UserPwdSlave userPwdSlave;

    @Autowired
    private UserPwdMaster userPwdMaster;

    @Autowired
    private OkHttpService okHttpService;
    /**
     * 根据手机号查询用户信息
     *
     * @param mobile
     * @return
     */
    @Override
    public User getUser(String mobile) {
        if (StringUtils.isEmpty(mobile)){
            return null;
        }

        UserQuery userQuery = new UserQuery();
        userQuery.setMobile(mobile);

        return userSlave.findOne(userQuery);
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @Override
    public User saveUser(User user) {
        if (null == user){
            return null;
        }

        user.setUid(idGen.getSeqId(User.class));

        return userMaster.save(user);
    }

    /**
     * 根据用户的uid，查询用户是否设置过密码
     *
     * @param uid
     * @return
     */
    @Override
    public UserPwd getUserPwd(Long uid) {
        if (null == uid) {
            return null;
        }
        UserPwdQuery userPwdQuery = new UserPwdQuery();
        userPwdQuery.setUid(uid);
        return userPwdSlave.findOne(userPwdQuery);
    }

    /**
     * 保存密码
     *
     * @param uid
     * @param pwd
     * @param passWordType
     * @return
     */
    @Override
    public UserPwd saveUserPwd(Long uid, String pwd, Integer passWordType) {
        if (null == uid || StringUtils.isEmpty(pwd) || null == passWordType) {
            return null;
        }
        UserPwd userPwd = new UserPwd();
        userPwd.setUid(uid);
        if (PassWordTypeEnum.LOGINPWD.getCode().equals(passWordType)) {
            userPwd.setLoginPwd(pwd);
        } else if (PassWordTypeEnum.PAYPWD.getCode().equals(passWordType)) {
            userPwd.setPayPwd(pwd);
        } else {
            return null;
        }
        return userPwdMaster.saveAndFlush(userPwd);
    }

    /**
     * 通过手机号去设置或者更新密码
     *
     * @param uid
     * @param pwd
     * @param passWordType
     * @return
     */
    @Override
    public Boolean updateUserPwd(Long uid, String pwd, Integer passWordType) {
        if (null == uid || StringUtils.isEmpty(pwd) || null == passWordType) {
            return false;
        }
        UserPwd userPwd = new UserPwd();
        userPwd.setUid(uid);
        if (PassWordTypeEnum.LOGINPWD.getCode().equals(passWordType)) {
            userPwd.setLoginPwd(pwd);
        } else {
            userPwd.setPayPwd(pwd);
        }
        return userPwdMaster.updateById(userPwd, uid, "loginPwd", "payPwd") > 0 ? true : false;
    }

    /**
     * 根据用户的uid获取用户的信息
     *
     * @param uid
     * @return
     */
    @Override
    public User getUser(Long uid) {
        if (null == uid){
            return null;
        }
        return userSlave.findOne(uid);
    }

}
