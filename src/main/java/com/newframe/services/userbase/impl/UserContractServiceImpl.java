package com.newframe.services.userbase.impl;

import com.newframe.blockchain.util.KeyUtil;
import com.newframe.entity.user.UserContract;
import com.newframe.repositories.dataMaster.user.UserContractMaster;
import com.newframe.repositories.dataQuery.user.UserContractQuery;
import com.newframe.repositories.dataSlave.user.UserContractSlave;
import com.newframe.services.userbase.UserContractService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserContractMaster userContractMaster;
    @Autowired
    private UserContractSlave userContractSlave;

    /**
     * 根据uid查询用户公私钥信息
     *
     * @param uid
     * @return
     */
    @Override
    public UserContract findOne(Long uid) {

        return userContractSlave.findOne(uid);
    }

    /**
     * 插入用户公私钥信息
     *
     * @param uid
     * @return
     */
    @Override
    public UserContract insert(Long uid) {

        if(uid == null){
            return null;
        }
        UserContract userContract = new UserContract();
        String privateKey = KeyUtil.privateKey();
        String publicKey = KeyUtil.publicKey(privateKey);
        userContract.setUid(uid);
        userContract.setPrivatekey(privateKey);
        userContract.setPublickey(publicKey);
        return userContractMaster.save(userContract);
    }

    /**
     * 删除
     *
     * @param uid
     */
    @Override
    public void delete(Long uid) {
        userContractMaster.deleteById(uid);
    }
}