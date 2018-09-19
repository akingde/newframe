package com.newframe.services.userbase;

import com.newframe.entity.user.UserContract;

/**
 *
 *  用户公私钥
 *
 * This class corresponds to the database table user_contract
 *
 * @author WangBin
 * @mbggenerated do_not_delete_during_merge
 */
public interface UserContractService {

    /**
     * 根据uid查询用户公私钥信息
     * @param uid
     * @return
     */
    UserContract findOne(Long uid);

    /**
     * 插入用户公私钥信息
     * @param uid
     * @return
     */
    UserContract insert(Long uid);

    /**
     * 删除
     * @param uid
     */
    void delete(Long uid);
}