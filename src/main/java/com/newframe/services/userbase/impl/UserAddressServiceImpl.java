package com.newframe.services.userbase.impl;

import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.UserAddress;
import com.newframe.repositories.dataMaster.user.UserAddressMaster;
import com.newframe.repositories.dataQuery.user.UserAddressQuery;
import com.newframe.repositories.dataSlave.user.UserAddressSlave;
import com.newframe.services.userbase.UserAddressService;
import com.newframe.utils.cache.IdGlobalGenerator;
import com.newframe.utils.query.BaseQuery;
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
        if(userAddress.getConsigneeName() != null){
            updateFields.add("consigneeName");
        }
        if(userAddress.getMobile() != null){
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
        if(userAddress.getConsigneeAddress() != null){
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
     * @param pageSearchDTO
     * @return
     */
    @Override
    public Page<UserAddress> findUserAddressList(Long uid, PageSearchDTO pageSearchDTO) {
        if(uid == null){
            return Page.empty();
        }
        UserAddressQuery query = new UserAddressQuery();
        query.setUid(uid);
        Sort sort = new Sort(Sort.Direction.DESC, "defaultAddress");
        query.setSort(sort);
        PageRequest pageRequest = PageRequest.of(pageSearchDTO.getCurrentPage() - 1, pageSearchDTO.getPageSize());
        return userAddressSlave.findAll(query, pageRequest);
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
        return userAddressSlave.findOne(query);
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
        return userAddressSlave.findOne(query);
    }
}
