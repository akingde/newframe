package com.newframe.services.userbase;

import com.newframe.entity.user.UserLoginHistory;

import java.util.List;

/**
 *
 *  登录历史
 *
 * This class corresponds to the database table user_login_history
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
public interface UserLoginHistoryService {

    /**
     * 插入用户登录历史
     * @param userLoginHistory
     * @return
     */
    UserLoginHistory insert(UserLoginHistory userLoginHistory);

    /**
     * 根据uid查找登录历史
     * @param uid
     * @return
     */
    List<UserLoginHistory> findByUid(Long uid);
}