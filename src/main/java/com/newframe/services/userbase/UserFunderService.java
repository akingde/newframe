package com.newframe.services.userbase;

import com.newframe.entity.user.UserFunder;

/**
 *
 *  资金方
 *
 * This class corresponds to the database table user_funder
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
public interface UserFunderService {

    /**
     * 查询
     * @param uid
     * @return
     */
    UserFunder findOne(Long uid);

    /**
     * 添加
     * @param userFunder
     * @return
     */
    UserFunder insert(UserFunder userFunder);

    /**
     * 更新
     * @param userFunder
     * @return
     */
    int update(UserFunder userFunder);

    /**
     * 删除
     * @param uid
     */
    void delete(Long uid);
}