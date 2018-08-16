package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserLoginHistory;
import com.newframe.services.userbase.UserLoginHistoryService;
import org.springframework.stereotype.Service;

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
@Service
public class UserLoginHistoryServiceImpl implements UserLoginHistoryService {

    /**
     * 插入用户登录历史
     *
     * @param userLoginHistory
     * @return
     */
    @Override
    public UserLoginHistory insert(UserLoginHistory userLoginHistory) {
        return null;
    }

    /**
     * 根据uid查找登录历史
     *
     * @param uid
     * @return
     */
    @Override
    public List<UserLoginHistory> findByUid(Long uid) {
        return null;
    }
}