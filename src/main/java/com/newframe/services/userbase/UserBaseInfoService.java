package com.newframe.services.userbase;

import com.newframe.entity.user.UserBaseInfo;

/**
 *
 *  用户的基本信息
 *
 * This class corresponds to the database table user_base_info
 *
 * @author WangBin
 * @mbggenerated do_not_delete_during_merge
 */
public interface UserBaseInfoService {

    /**
     * 插入用户基本信息
     * @param userBaseInfo
     * @return
     */
    UserBaseInfo insert(UserBaseInfo userBaseInfo);

    /**
     * 根据uid修改用户的基本信息
     * @param userBaseInfo
     * @return
     */
    int updateByUid(UserBaseInfo userBaseInfo);

    /**
     * 根据uid查询用户基本信息
     * @param uid
     * @return
     */
    UserBaseInfo findOne(Long uid);

    /**
     * 根据uid删除角色基本信息
     * @param uid
     * @return
     */
    int removeByUid(Long uid);
}