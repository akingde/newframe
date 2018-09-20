package com.newframe.services.userbase.impl;

import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.UserRentMerchant;
import com.newframe.repositories.dataMaster.user.UserRentMerchantMaster;
import com.newframe.repositories.dataQuery.user.UserRentMerchantQuery;
import com.newframe.repositories.dataSlave.user.UserRentMerchantSlave;
import com.newframe.services.userbase.UserRentMerchantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
     * @return
     */
    @Override
    public UserRentMerchant findOne(Long uid) {
        UserRentMerchantQuery query = new UserRentMerchantQuery();
        query.setUid(uid);
        return userRentMerchantSlave.findOne(query);
    }

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
        return userRentMerchantSlave.findOne(query);
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
        if(StringUtils.isNotEmpty(userRentMerchant.getMerchantPhoneNumber())){
            updateFields.add("merchantPhoneNumber");
        }
        if (StringUtils.isNotEmpty(userRentMerchant.getMerchantName())){
            updateFields.add("merchantName");
        }
        if (StringUtils.isNotEmpty(userRentMerchant.getMerchantPhoneNumber())){
            updateFields.add("merchantPhoneNumber");
        }
        if(StringUtils.isNotEmpty(userRentMerchant.getLegalEntity())){
            updateFields.add("legalEntity");
        }
        if(StringUtils.isNotEmpty(userRentMerchant.getLegalEntityIdNumber())){
            updateFields.add("legalEntityIdNumber");
        }
        if(StringUtils.isNotEmpty(userRentMerchant.getBusinessLicenseNumber())){
            updateFields.add("businessLicenseNumber");
        }
        if(StringUtils.isNotEmpty(userRentMerchant.getRentMerchantAddress())){
            updateFields.add("rentMerchantAddress");
        }
        if(StringUtils.isNotEmpty(userRentMerchant.getBusinessLicenseFile())){
            updateFields.add("businessLicenseFile");
        }
        if(StringUtils.isNotEmpty(userRentMerchant.getHighestDegreeDiplomaFile())){
            updateFields.add("highestDegreeDiplomaFile");
        }
        if(StringUtils.isNotEmpty(userRentMerchant.getDrivingLicenseFile())){
            updateFields.add("drivingLicenseFile");
        }
        if(StringUtils.isNotEmpty(userRentMerchant.getHouseProprietaryCertificateFile())){
            updateFields.add("houseProprietaryCertificateFile");
        }
        if(userRentMerchant.getRoleStatus() != null){
            updateFields.add("roleStatus");
        }
        if(userRentMerchant.getProvinceId() != null){
            updateFields.add("provinceId");
            updateFields.add("provinceName");
        }
        if(userRentMerchant.getCityId() != null){
            updateFields.add("cityId");
            updateFields.add("cityName");
        }
        if(userRentMerchant.getCountyId() != null){
            updateFields.add("countyId");
            updateFields.add("countyName");
        }
        if(StringUtils.isNotEmpty(userRentMerchant.getConsigneeAddress())){
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
     * 根据手机号查找
     *
     * @param phoneNumber
     * @return
     */
    @Override
    public UserRentMerchant findOne(String phoneNumber) {
        UserRentMerchantQuery query = new UserRentMerchantQuery();
        query.setPhoneNumber(phoneNumber);
        return userRentMerchantSlave.findOne(query);
    }

    /**
     * 获取小B列表
     *
     * @param parentUid
     * @return
     */
    @Override
    public Page<UserRentMerchant> findAll(Long parentUid, PageSearchDTO pageSearchDTO) {
        UserRentMerchantQuery query = new UserRentMerchantQuery();
        query.setParentId(parentUid);
        Sort sort = new Sort(Sort.Direction.DESC, "ctime");
        Pageable page = PageRequest.of(pageSearchDTO.getCurrentPage() - 1, pageSearchDTO.getPageSize(), sort);
        return userRentMerchantSlave.findAll(query, page);
    }
}