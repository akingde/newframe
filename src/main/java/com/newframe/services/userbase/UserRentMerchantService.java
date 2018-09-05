package com.newframe.services.userbase;

import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.UserRentMerchant;
import org.springframework.data.domain.Page;

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
public interface UserRentMerchantService {

    /**
     * 根据用户id和角色id获取租赁商信息
     * @param uid
     * @return
     */
    UserRentMerchant findOne(Long uid);

    /**
     * 根据用户id和角色id获取租赁商信息
     * @param uid
     * @param roleId
     * @return
     */
    UserRentMerchant findOne(Long uid, Integer roleId);

    /**
     * 插入用户租赁商信息
     * @param userRentMerchant
     * @return
     */
    UserRentMerchant insert(UserRentMerchant userRentMerchant);

    /**
     * 更新用户租赁商信息
     * @param userRentMerchant
     * @return
     */
    int update(UserRentMerchant userRentMerchant);

    /**
     * 删除用户租赁商
     * @param uid
     */
    void delete(Long uid);

    /**
     * 获取小B列表
     * @param parentUid
     * @return
     */
    Page<UserRentMerchant> findAll(Long parentUid, PageSearchDTO pageSearchDTO);
}