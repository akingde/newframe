package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserHirer;
import com.newframe.repositories.dataMaster.user.UserHirerMaster;
import com.newframe.repositories.dataQuery.user.UserHirerQuery;
import com.newframe.repositories.dataSlave.user.UserHirerSlave;
import com.newframe.services.userbase.UserHirerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *  出租方
 *
 * This class corresponds to the database table user_hirer
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
@Service
public class UserHirerServiceImpl implements UserHirerService {

    @Autowired
    private UserHirerMaster userHirerMaster;
    @Autowired
    private UserHirerSlave userHirerSlave;

    /**
     * 查找出租方信息
     *
     * @param uid
     * @return
     */
    @Override
    public UserHirer findOne(Long uid) {
        if (null == uid){
            return null;
        }

        return userHirerSlave.findOne(uid);
    }

    /**
     * 增加
     *
     * @param userHirer
     * @return
     */
    @Override
    public UserHirer insert(UserHirer userHirer) {
        if(userHirer == null){
            return null;
        }
        return userHirerMaster.save(userHirer);
    }

    /**
     * 修改
     *
     * @param userHirer
     * @return
     */
    @Override
    public int update(UserHirer userHirer) {
        if(userHirer == null){
            return 0;
        }
        List<String> updateFields = new ArrayList();
        UserHirerQuery query = new UserHirerQuery();
        query.setUid(userHirer.getUid());
        if(userHirer.getMerchantName() != null){
            updateFields.add("merchantName");
        }
        if(userHirer.getLegalEntity() != null){
            updateFields.add("legalEntity");
        }
        if(userHirer.getLegalEntityIdNumber() != null){
            updateFields.add("legalEntityIdNumber");
        }
        if(userHirer.getTopContacts() != null){
            updateFields.add("topContacts");
        }
        if(userHirer.getTopContactsPhoneNumber() != null){
            updateFields.add("topContactsPhoneNumber");
        }
        if(userHirer.getRelationship() != null){
            updateFields.add("relationship");
        }
        if(userHirer.getBusinessLicenseNumber() != null){
            updateFields.add("businessLicenseNumber");
        }
        if(userHirer.getBusinessLicenseFile() != null){
            updateFields.add("businessLicenseFile");
        }
        if(userHirer.getRoleStatus() != null){
            updateFields.add("roleStatus");
        }
        String[] array = new String[updateFields.size()];
        updateFields.toArray(array);
        return userHirerMaster.update(userHirer, query, array);
    }

    /**
     * 删除
     *
     * @param uid
     */
    @Override
    public void remove(Long uid) {
        userHirerMaster.deleteById(uid);
    }
}