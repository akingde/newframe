package com.newframe.services.userbase.impl;

import com.google.common.collect.Lists;
import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.UserAddress;
import com.newframe.repositories.dataMaster.user.UserAddressMaster;
import com.newframe.repositories.dataQuery.user.UserAddressQuery;
import com.newframe.repositories.dataSlave.user.UserAddressSlave;
import com.newframe.services.userbase.UserAddressService;
import com.newframe.utils.cache.IdGlobalGenerator;
import com.newframe.utils.query.BaseQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.time.Clock.systemUTC;

/**
 * 用户的地址管理服务
 * <p>
 * This class corresponds to the database table user_address
 *
 * @mbggenerated do_not_delete_during_merge
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMaster userAddressMaster;
    @Autowired
    private UserAddressSlave userAddressSlave;
    @Autowired
    private IdGlobalGenerator idGlobalGenerator;

    /**
     * 添加用户地址
     *
     * @param userAddress
     * @return
     */
    @Override
    public UserAddress insert(UserAddress userAddress) {
        if (userAddress == null) {
            return null;
        }
        userAddress.setId(idGlobalGenerator.getSeqId(UserAddress.class));
        return userAddressMaster.save(userAddress);
    }

    /**
     * 根据地址id修改用户的地址
     *
     * @param userAddress
     * @return
     */
    @Override
    public int updateByAddressId(UserAddress userAddress) {
        if (userAddress == null) {
            return 0;
        }
        List<String> updateFields = new ArrayList();
        if(StringUtils.isNotEmpty(userAddress.getConsigneeName())){
            updateFields.add("consigneeName");
        }
        if(StringUtils.isNotEmpty(userAddress.getMobile())){
            updateFields.add("mobile");
        }
        if(userAddress.getProvinceId() != null){
            updateFields.add("provinceId");
            updateFields.add("provinceName");
        }
        if(userAddress.getCityId() != null){
            updateFields.add("cityId");
            updateFields.add("cityName");
        }
        if(userAddress.getCountyId() != null){
            updateFields.add("countyId");
            updateFields.add("countyName");
        }
        if(StringUtils.isNotEmpty(userAddress.getConsigneeAddress())){
            updateFields.add("consigneeAddress");
        }
        if (userAddress.getDefaultAddress() != null){
            updateFields.add("defaultAddress");
        }
        String[] array =new String[updateFields.size()];
        updateFields.toArray(array);
        return userAddressMaster.updateById(userAddress, userAddress.getId(), array);
    }

    /**
     * 根据地址id删除地址
     *
     * @param addressId
     * @return
     */
    @Override
    public void removeByAddressId(Long addressId) {
        if(addressId != null) {
            userAddressMaster.deleteById(addressId);
        }
    }

    /**
     * 根据用户id查询用户的地址列表
     *
     * @param uid
     * @return
     */
    @Override
    public List<UserAddress> findUserAddressList(Long uid) {
        if(uid == null){
            return Lists.newArrayList();
        }
        UserAddressQuery query = new UserAddressQuery();
        query.setUid(uid);
        Sort sort = new Sort(Sort.Direction.DESC, "defaultAddress");
        return userAddressSlave.findAll(query, sort);
    }

    /**
     * 获取默认地址
     *
     * @param uid
     * @return
     */
    @Override
    public UserAddress findDefaultAddress(Long uid) {
        UserAddressQuery query = new UserAddressQuery();
        query.setUid(uid);
        query.setDefaultAddress(true);
        //return userAddressSlave.findOne(query);
        //findOne，现在更新了，适用于根据唯一主键之间查，底层执行的是findByID
        //其余均有可能有多个值，请使用findAll()，以免数据库存在多个值查询抛错
        return null;
    }

    /**
     * 根据地址id查找地址
     *
     * @param addressId
     * @return
     */
    @Override
    public UserAddress findAddress(Long addressId, Long uid) {
        UserAddressQuery query = new UserAddressQuery();
        query.setId(addressId);
        query.setUid(uid);
        //findOne，现在更新了，适用于根据唯一主键之间查，底层执行的是findByID
        //其余均有可能有多个值，请使用findAll()，以免数据库存在多个值查询抛错
        return null;
    }
}
