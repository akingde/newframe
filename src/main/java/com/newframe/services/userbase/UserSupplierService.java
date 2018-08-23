package com.newframe.services.userbase;

import com.newframe.entity.user.UserSupplier;

import java.util.List;

/**
 *
 *  供应商
 *
 * This class corresponds to the database table user_supplier
 *
 * @mbggenerated do_not_delete_during_merge
 */
public interface UserSupplierService {

    /**
     * 获取所有的供应商
     * @return
     */
    List<UserSupplier> findAll();

    /**
     * 查询
     * @param uid
     * @return
     */
    UserSupplier findOne(Long uid);

    /**
     * 根据uid获取
     * @param supplierUids
     * @return
     */
    List<UserSupplier> findAll(List<Long> supplierUids);

    /**
     * 添加
     * @param userSupplier
     * @return
     */
    UserSupplier insert(UserSupplier userSupplier);

    /**
     * 更新
     * @param userSupplier
     * @return
     */
    int update(UserSupplier userSupplier);

    /**
     * 删除
     * @param uid
     */
    void delete(Long uid);
}