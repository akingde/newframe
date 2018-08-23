package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserRentMerchant;
import com.newframe.repositories.dataMaster.user.UserRentMerchantMaster;
import com.newframe.repositories.dataQuery.user.UserRentMerchantQuery;
import com.newframe.repositories.dataSlave.user.UserRentMerchantSlave;
import com.newframe.services.userbase.UserRentMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *  租赁商
 *
 * This class corresponds to the database table user_rent_merchant
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
@Service
public class UserRentMerchantServiceImpl implements UserRentMerchantService {

    @Autowired
    private UserRentMerchantMaster userRentMerchantMaster;
    @Autowired
    private UserRentMerchantSlave userRentMerchantSlave;

    /**
     * 根据用户id和角色id获取租赁商信息
     *
     * @param uid
     * @param roleId
     * @return
     */
    @Override
    public UserRentMerchant findOne(Long uid, Integer roleId) {
        UserRentMerchantQuery query = new UserRentMerchantQuery();
        query.setUid(uid);
        query.setRoleId(roleId);
        //return userRentMerchantSlave.findOne(query).get();
        return null;
    }

    /**
     * 插入用户租赁商信息
     *
     * @param userRentMerchant
     * @return
     */
    @Override
    public UserRentMerchant insert(UserRentMerchant userRentMerchant) {
        if(userRentMerchant == null) {
            return null;
        }
        return userRentMerchantMaster.save(userRentMerchant);
    }

    /**
     * 更新用户租赁商信息
     *
     * @param userRentMerchant
     * @return
     */
    @Override
    public int update(UserRentMerchant userRentMerchant) {
        if (userRentMerchant == null || userRentMerchant.getUid() == null) {
            return 0;
        }
        List<String> updateFields = new ArrayList();
        UserRentMerchantQuery query = new UserRentMerchantQuery();
        query.setUid(userRentMerchant.getUid());
        if (userRentMerchant.getMerchantName() != null){
            updateFields.add("merchantName");
        }
        if(userRentMerchant.getLegalEntity() != null){
            updateFields.add("legalEntity");
        }
        if(userRentMerchant.getLegalEntityIdNumber() != null){
            updateFields.add("legalEntityIdNumber");
        }
        if(userRentMerchant.getBusinessLicenseNumber() != null){
            updateFields.add("businessLicenseNumber");
        }
        if(userRentMerchant.getRentMerchantAddress() != null){
            updateFields.add("rentMerchantAddress");
        }
        if(userRentMerchant.getBusinessLicenseFile() != null){
            updateFields.add("businessLicenseFile");
        }
        if(userRentMerchant.getHighestDegreeDiplomaFile() != null){
            updateFields.add("highestDegreeDiplomaFile");
        }
        if(userRentMerchant.getDrivingLicenseFile() != null){
            updateFields.add("drivingLicenseFile");
        }
        if(userRentMerchant.getHouseProprietaryCertificateFile() != null){
            updateFields.add("houseProprietaryCertificateFile");
        }
        if(userRentMerchant.getRoleStatus() != null){
            updateFields.add("roleStatus");
        }
        if(userRentMerchant.getProvinceId() != null){
            updateFields.add("provinceId");
        }
        if(userRentMerchant.getProvinceName() != null){
            updateFields.add("provinceName");
        }
        if(userRentMerchant.getCityId() != null){
            updateFields.add("cityId");
        }
        if(userRentMerchant.getCityName() != null){
            updateFields.add("cityName");
        }
        if(userRentMerchant.getCountyId() != null){
            updateFields.add("countyId");
        }
        if(userRentMerchant.getCountyName() != null){
            updateFields.add("countyName");
        }
        if(userRentMerchant.getConsigneeAddress() != null){
            updateFields.add("consigneeAddress");
        }
        if(userRentMerchant.getAppoint() != null){
            updateFields.add("appoint");
        }
        String[] array = new String[updateFields.size()];
        updateFields.toArray(array);
        return userRentMerchantMaster.update(userRentMerchant, query, array);
    }

    /**
     * 删除用户租赁商
     *
     * @param uid
     */
    @Override
    public void delete(Long uid) {
        userRentMerchantMaster.deleteById(uid);
    }
}