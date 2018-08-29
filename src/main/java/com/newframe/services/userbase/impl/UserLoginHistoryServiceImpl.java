package com.newframe.services.userbase.impl;

import com.google.common.collect.Lists;
import com.newframe.entity.user.UserLoginHistory;
import com.newframe.repositories.dataMaster.user.UserLoginHistoryMaster;
import com.newframe.repositories.dataQuery.user.UserLoginHistoryQuery;
import com.newframe.repositories.dataSlave.user.UserLoginHistorySlave;
import com.newframe.services.userbase.UserLoginHistoryService;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    @Autowired
    private UserLoginHistoryMaster userLoginHistoryMaster;
    @Autowired
    private UserLoginHistorySlave userLoginHistorySlave;
    @Autowired
    private IdGlobalGenerator idGlobalGenerator;

    /**
     * 插入用户登录历史
     *
     * @param userLoginHistory
     * @return
     */
    @Override
    public UserLoginHistory insert(UserLoginHistory userLoginHistory) {
        if (userLoginHistory == null) {
            return null;
        }
        userLoginHistory.setId(idGlobalGenerator.getSeqId(UserLoginHistory.class));
        return userLoginHistoryMaster.save(userLoginHistory);
    }

    /**
     * 根据uid查找登录历史
     *
     * @param uid
     * @return
     */
    @Override
    public List<UserLoginHistory> findByUid(Long uid) {
        UserLoginHistoryQuery query = new UserLoginHistoryQuery();
        query.setUid(uid);
        Sort sort = new Sort(Sort.Direction.DESC, "ctime");
        PageRequest pageRequest = PageRequest.of(0,10, sort);
        Page<UserLoginHistory> pageResult = userLoginHistorySlave.findAll(query, pageRequest);
        return pageResult == null ? Lists.newArrayList() : pageResult.getContent();
    }
}