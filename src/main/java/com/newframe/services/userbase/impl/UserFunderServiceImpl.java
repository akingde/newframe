package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserFunder;
import com.newframe.repositories.dataMaster.user.UserFunderMaster;
import com.newframe.repositories.dataQuery.user.UserFunderQuery;
import com.newframe.repositories.dataSlave.user.UserFunderSlave;
import com.newframe.services.userbase.UserFunderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *  资金方
 *
 * This class corresponds to the database table user_funder
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
@Service
public class UserFunderServiceImpl implements UserFunderService {

    @Autowired
    private UserFunderMaster userFunderMaster;
    @Autowired
    private UserFunderSlave userFunderSlave;

    /**
     * 查询
     *
     * @param uid
     * @return
     */
    @Override
    public UserFunder findOne(Long uid) {
        if (null == uid){
            return null;
        }
        return userFunderSlave.findOne(uid);
    }

    /**
     * 添加
     *
     * @param userFunder
     * @return
     */
    @Override
    public UserFunder insert(UserFunder userFunder) {
        return userFunder == null ? null : userFunderMaster.save(userFunder);
    }

    /**
     * 更新
     *
     * @param userFunder
     * @return
     */
    @Override
    public int update(UserFunder userFunder) {
        List<String> updateFields = new ArrayList();
        UserFunderQuery query = new UserFunderQuery();
        query.setUid(userFunder.getUid());
        if(userFunder.getMerchantName() != null){
            updateFields.add("merchantName");
        }
        if(userFunder.getLegalEntity() != null){
            updateFields.add("legalEntity");
        }
        if(userFunder.getLegalEntityIdNumber() != null){
            updateFields.add("legalEntityIdNumber");
        }
        if(userFunder.getTopContacts() != null){
            updateFields.add("topContacts");
        }
        if(userFunder.getTopContactsPhoneNumber() != null){
            updateFields.add("topContactsPhoneNumber");
        }
        if(userFunder.getRelationship() != null){
            updateFields.add("relationship");
        }
        if(userFunder.getBusinessLicenseNumber() != null){
            updateFields.add("businessLicenseNumber");
        }
        if(userFunder.getBusinessLicenseFile() != null){
            updateFields.add("businessLicenseFile");
        }
        if(userFunder.getLetterOfAttorneyFile() != null){
            updateFields.add("letterOfAttorneyFile");
        }
        if(userFunder.getBusinessQualificationFile() != null){
            updateFields.add("businessQualificationFile");
        }
        if(userFunder.getRoleStatus() != null){
            updateFields.add("roleStatus");
        }
        String[] array = new String[updateFields.size()];
        updateFields.toArray(array);
        return userFunderMaster.update(userFunder, query, array);
    }

    /**
     * 删除
     *
     * @param uid
     */
    @Override
    public void delete(Long uid) {
        userFunderMaster.deleteById(uid);
    }
}