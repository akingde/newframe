package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserSupplier;
import com.newframe.repositories.dataMaster.user.UserSupplierMaster;
import com.newframe.repositories.dataQuery.user.UserSupplierQuery;
import com.newframe.repositories.dataSlave.user.UserSupplierSlave;
import com.newframe.services.userbase.UserSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *  供应商
 *
 * This class corresponds to the database table user_supplier
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
@Service
public class UserSupplierServiceImpl implements UserSupplierService {

    @Autowired
    private UserSupplierMaster userSupplierMaster;
    @Autowired
    private UserSupplierSlave userSupplierSlave;

    /**
     * 获取所有的供应商
     *
     * @return
     */
    @Override
    public List<UserSupplier> findAll() {
        return userSupplierSlave.findAll();
    }

    /**
     * 查询
     *
     * @param uid
     * @return
     */
    @Override
    public UserSupplier findOne(Long uid) {
        if (null == uid){
            return null;
        }
        return userSupplierSlave.findOne(uid);
    }

    /**
     * 添加
     *
     * @param userSupplier
     * @return
     */
    @Override
    public UserSupplier insert(UserSupplier userSupplier) {
        return userSupplier == null ? null : userSupplierMaster.save(userSupplier);
    }

    /**
     * 更新
     *
     * @param userSupplier
     * @return
     */
    @Override
    public int update(UserSupplier userSupplier) {

        if(userSupplier == null){
            return 0;
        }
        List<String> updateFields = new ArrayList();
        UserSupplierQuery query = new UserSupplierQuery();
        query.setUid(userSupplier.getUid());
        if(userSupplier.getMerchantName() != null){
            updateFields.add("merchantName");
        }
        if(userSupplier.getLegalEntity() != null){
            updateFields.add("legalEntity");
        }
        if(userSupplier.getLegalEntityIdNumber() != null){
            updateFields.add("legalEntityIdNumber");
        }
        if(userSupplier.getBusinessLicenseNumber() != null){
            updateFields.add("businessLicenseNumber");
        }
        if(userSupplier.getBusinessLicenseFile() != null){
            updateFields.add("businessLicenseFile");
        }
        if(userSupplier.getRoleStatus() != null){
            updateFields.add("roleStatus");
        }
        String[] array = new String[updateFields.size()];
        updateFields.toArray(array);
        return userSupplierMaster.update(userSupplier, query, array);
    }

    /**
     * 删除
     *
     * @param uid
     */
    @Override
    public void delete(Long uid) {
        userSupplierMaster.deleteById(uid);
    }
}