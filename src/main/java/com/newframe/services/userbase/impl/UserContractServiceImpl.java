package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserContract;
import com.newframe.services.userbase.UserContractService;
import org.springframework.stereotype.Service;

/**
 *
 *  用户公私钥
 *
 * This class corresponds to the database table user_contract
 *
 * @author WangBin
 * @mbggenerated do_not_delete_during_merge
 */
@Service
public class UserContractServiceImpl implements UserContractService {

    /**
     * 根据uid查询用户公私钥信息
     *
     * @param uid
     * @return
     */
    @Override
    public UserContract findOne(Long uid) {
        return null;
    }

    /**
     * 插入用户公私钥信息
     *
     * @param userContract
     * @return
     */
    @Override
    public UserContract insert(UserContract userContract) {
        return null;
    }
}