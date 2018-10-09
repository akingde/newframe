package com.newframe.services.user;

import com.newframe.entity.user.User;

/**
 * @author:wangdong
 * @description:用户原子类的服务
 */
public interface UserService {

    /**
     * 根据手机号查询用户信息
     * @param mobile
     * @return
     */
    User getUser(String mobile);

    /**
     * 保存用户
     * @param user
     * @return
     */
    User saveUser(User user);
}
