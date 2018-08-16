package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserBaseInfo;
import com.newframe.services.userbase.UserBaseInfoService;
import org.springframework.stereotype.Service;

/**
 *
 *  用户的基本信息
 *
 * This class corresponds to the database table user_base_info
 *
 * @author WangBin
 * @mbggenerated do_not_delete_during_merge
 */
@Service
public class UserBaseInfoServiceImpl implements UserBaseInfoService {

    /**
     * 插入用户基本信息
     *
     * @param userBaseInfo
     * @return
     */
    @Override
    public UserBaseInfo insert(UserBaseInfo userBaseInfo) {
        return null;
    }

    /**
     * 根据uid修改用户的基本信息
     *
     * @param userBaseInfo
     * @return
     */
    @Override
    public int updateByUid(UserBaseInfo userBaseInfo) {
        return 0;
    }

    /**
     * 根据uid查询用户基本信息
     *
     * @param uid
     * @return
     */
    @Override
    public UserBaseInfo findOne(Long uid) {
        return null;
    }

    /**
     * 根据uid删除角色基本信息
     *
     * @param uid
     * @return
     */
    @Override
    public int removeByUid(Long uid) {
        return 0;
    }
}