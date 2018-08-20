package com.newframe.services.userbase;

import com.newframe.entity.user.UserSupplier;

/**
 *
 *  供应商
 *
 * This class corresponds to the database table user_supplier
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
public interface UserSupplierService {

    /**
     * 查询
     * @param uid
     * @return
     */
    UserSupplier findOne(Long uid);

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