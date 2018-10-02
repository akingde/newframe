package com.newframe.services.userbase.impl;

import com.google.common.collect.Lists;
import com.newframe.entity.user.UserHirer;
import com.newframe.enums.user.RoleStatusEnum;
import com.newframe.repositories.dataMaster.user.UserHirerMaster;
import com.newframe.repositories.dataQuery.user.UserHirerQuery;
import com.newframe.repositories.dataSlave.user.UserHirerSlave;
import com.newframe.services.userbase.UserHirerService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
     * 获取所有的出租方
     *
     * @return
     */
    @Override
    public List<UserHirer> findAll() {
        UserHirerQuery query = new UserHirerQuery();
        query.setRoleStatus(RoleStatusEnum.NORMAL.getRoleStatue());
        List<UserHirer> hirers = userHirerSlave.findAll(query);
        return CollectionUtils.isEmpty(hirers) ? Lists.newArrayList() : hirers;
    }

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
        if(StringUtils.isNotEmpty(userHirer.getPhoneNumber())){
            updateFields.add("phoneNumber");
        }
        if(StringUtils.isNotEmpty(userHirer.getMerchantName())){
            updateFields.add("merchantName");
        }
        if(StringUtils.isNotEmpty(userHirer.getLegalEntity())){
            updateFields.add("legalEntity");
        }
        if(StringUtils.isNotEmpty(userHirer.getLegalEntityIdNumber())){
            updateFields.add("legalEntityIdNumber");
        }
        if(StringUtils.isNotEmpty(userHirer.getTopContacts())){
            updateFields.add("topContacts");
        }
        if(StringUtils.isNotEmpty(userHirer.getTopContactsPhoneNumber())){
            updateFields.add("topContactsPhoneNumber");
        }
        if(userHirer.getRelationship() != null){
            updateFields.add("relationship");
        }
        if(StringUtils.isNotEmpty(userHirer.getBusinessLicenseNumber())){
            updateFields.add("businessLicenseNumber");
        }
        if(StringUtils.isNotEmpty(userHirer.getBusinessLicenseFile())){
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