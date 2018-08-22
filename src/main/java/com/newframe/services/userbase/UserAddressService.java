package com.newframe.services.userbase;

import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.UserAddress;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *  用户的地址管理服务
 *
 * This class corresponds to the database table user_address
 *
 * @mbggenerated do_not_delete_during_merge
 */
public interface UserAddressService {

    /**
     * 添加用户地址
     * @param userAddress
     * @return
     */
    UserAddress insert(UserAddress userAddress);

    /**
     * 根据地址id修改用户的地址
     * @param userAddress
     * @return
     */
    int updateByAddressId(UserAddress userAddress);

    /**
     * 根据地址id删除地址
     * @param addressId
     * @return
     */
    void removeByAddressId(Long addressId);

    /**
     * 根据用户id查询用户的地址列表
     * @param uid
     * @param pageSearchDTO
     * @return
     */
    Page<UserAddress> findUserAddressList(Long uid, PageSearchDTO pageSearchDTO);

    /**
     * 获取默认地址
     * @param uid
     * @return
     */
    UserAddress findDefaultAddress(Long uid);

    /**
     * 根据地址id和用户id查找地址
     * @param uid
     * @param addressId
     * @return
     */
    UserAddress findAddress(Long addressId, Long uid);
}
