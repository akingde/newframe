package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserSupplier;
import com.newframe.repositories.dataMaster.user.UserSupplierMaster;
import com.newframe.repositories.dataQuery.user.UserSupplierQuery;
import com.newframe.repositories.dataSlave.user.UserSupplierSlave;
import com.newframe.services.userbase.UserSupplierService;
import org.apache.commons.lang3.StringUtils;
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
     * 根据uid获取
     *
     * @param supplierUids
     * @return
     */
    @Override
    public List<UserSupplier> findAll(List<Long> supplierUids) {
        UserSupplierQuery query = new UserSupplierQuery();
        query.setUids(supplierUids);
        return userSupplierSlave.findAll(query);
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
        if(StringUtils.isNotEmpty(userSupplier.getMerchantName())){
            updateFields.add("merchantName");
        }
        if(StringUtils.isNotEmpty(userSupplier.getLegalEntity())){
            updateFields.add("legalEntity");
        }
        if(StringUtils.isNotEmpty(userSupplier.getLegalEntityIdNumber())){
            updateFields.add("legalEntityIdNumber");
        }
        if(StringUtils.isNotEmpty(userSupplier.getBusinessLicenseNumber())){
            updateFields.add("businessLicenseNumber");
        }
        if(StringUtils.isNotEmpty(userSupplier.getBusinessLicenseFile())){
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