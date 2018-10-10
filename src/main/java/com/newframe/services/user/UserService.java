package com.newframe.services.user;

import com.newframe.entity.user.User;
import com.newframe.entity.user.UserPwd;

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

    /**
     * 根据用户的uid，查询用户是否设置过密码
     *
     * @param uid
     * @return
     */
    UserPwd getUserPwd(Long uid);

    /**
     * 保存密码
     * @param uid
     * @param pwd
     * @param passWordType
     * @return
     */
    UserPwd saveUserPwd(Long uid, String pwd, Integer passWordType);

    /**
     * 更新密码
     * @param uid
     * @param pwd
     * @param passWordType
     * @return
     */
    Boolean updateUserPwd(Long uid, String pwd, Integer passWordType);
}
