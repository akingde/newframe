package com.newframe.services.user.impl;

import com.newframe.entity.user.User;
import com.newframe.repositories.dataMaster.user.UserMaster;
import com.newframe.repositories.dataQuery.user.UserQuery;
import com.newframe.repositories.dataSlave.user.UserSlave;
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
}
