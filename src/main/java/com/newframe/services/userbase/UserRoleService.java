package com.newframe.services.userbase;

import com.newframe.entity.user.UserRole;

import java.util.List;

/**
 *
 *  用户角色关联
 *
 * This class corresponds to the database table user_role
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
public interface UserRoleService {

    /**
     * 根据用户id和角色id找出用户的角色关系 状态非必选
     * @param uid
     * @param roleId
     * @param status
     * @return
     */
    UserRole findOne(Long uid, Integer roleId, Integer status);

    /**
     * 根据uid获取用户的角色列表  状态，角色id非必选
     * @param userRole
     * @return
     */
    List<UserRole> findUserRole(UserRole userRole);

    /**
     * 添加用户角色
     * @param userRole
     * @return
     */
    UserRole insert(UserRole userRole);

    /**
     * 批量添加角色
     * @param uid
     * @param roleIds
     * @return
     */
    List<UserRole> batchInsert(Long uid, Integer[] roleIds);

    /**
     * 更新用户角色
     * @param userRole
     * @return
     */
    int update(UserRole userRole);

    /**
     * 删除用户角色
     * @param userRole
     */
    void deleteById(UserRole userRole);
}