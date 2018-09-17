package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserBank;
import com.newframe.repositories.dataMaster.user.UserBankMaster;
import com.newframe.repositories.dataQuery.user.UserBankQuery;
import com.newframe.repositories.dataSlave.user.UserBankSlave;
import com.newframe.services.userbase.UserBankService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangBin
 */
@Service
public class UserBankServiceImpl implements UserBankService {

    @Autowired
    private UserBankMaster userBankMaster;
    @Autowired
    private UserBankSlave userBankSlave;

    @Override
    public UserBank insert(UserBank userBank) {
        if (userBank == null){
            return  null;
        }
        return userBankMaster.save(userBank);
    }

    @Override
    public int update(UserBank userBank) {
        if (userBank == null || userBank.getUid() == null){
            return 0;
        }
        UserBankQuery query = new UserBankQuery();
        query.setUid(userBank.getUid());
        List<String> updateFields = new ArrayList();
        if(StringUtils.isNotEmpty(userBank.getBankName())){
            updateFields.add("bankName");
        }
        if(StringUtils.isNotEmpty(userBank.getBankDetailedName())){
            updateFields.add("bankDetailedName");
        }
        if(StringUtils.isNotEmpty(userBank.getBankNumber())){
            updateFields.add("bankNumber");
        }
        if(StringUtils.isNotEmpty(userBank.getBankUserName())){
            updateFields.add("bankUserName");
        }
        if(StringUtils.isNotEmpty(userBank.getBankPhoneNumber())){
            updateFields.add("bankPhoneNumber");
        }
        String[] array = new String[updateFields.size()];
        updateFields.toArray(array);
        return userBankMaster.update(userBank, query, array);
    }

    @Override
    public UserBank findOne(Long uid) {
        if(uid == null){
            return null;
        }
        UserBankQuery query = new UserBankQuery();
        query.setUid(uid);
        return userBankSlave.findOne(query);
    }

    @Override
    public UserBank findOne(String bankNumber) {
        UserBankQuery query = new UserBankQuery();
        query.setBankNumber(bankNumber);
        return userBankSlave.findOne(query);
    }
}
