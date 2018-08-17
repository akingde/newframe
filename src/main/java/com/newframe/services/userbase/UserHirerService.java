package com.newframe.services.userbase;

import com.newframe.entity.user.UserHirer;

/**
 *
 *  出租方
 *
 * This class corresponds to the database table user_hirer
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
public interface UserHirerService {

    /**
     * 查找出租方信息
     * @param uid
     * @return
     */
    UserHirer findOne(Long uid);

    /**
     * 增加
     * @param userHirer
     * @return
     */
    UserHirer insert(UserHirer userHirer);

    /**
     * 修改
     * @param userHirer
     * @return
     */
    int update(UserHirer userHirer);

    /**
     * 删除
     * @param uid
     */
    void remove(Long uid);
}