package com.newframe.services.userbase.impl;

import com.newframe.entity.user.UserRole;
import com.newframe.repositories.dataMaster.user.UserRoleMaster;
import com.newframe.repositories.dataQuery.user.UserRoleQuery;
import com.newframe.repositories.dataSlave.user.UserRoleSlave;
import com.newframe.services.userbase.UserRoleService;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMaster userRoleMaster;
    @Autowired
    private UserRoleSlave userRoleSlave;
    @Autowired
    private IdGlobalGenerator idGlobalGenerator;

    /**
     * 根据用户id和角色id找出用户的角色关系 状态非必选
     * @param uid
     * @param roleId
     * @param status
     * @return
     */
    @Override
    public UserRole findOne(Long uid, Integer roleId, Integer status) {
        if(uid==null || roleId == null) {
            return null;
        }
        UserRoleQuery query = new UserRoleQuery();
        query.setUid(uid);
        query.setRoleId(roleId);
        if(status != null){
            query.setRoleStatus(status);
        }
        //return userRoleSlave.findOne(query).get();
        return null;
    }

    /**
     * 根据uid获取用户的角色列表  状态，角色id非必选
     *
     * @param userRole
     * @return
     */
    @Override
    public List<UserRole> findUserRole(UserRole userRole) {
        if(userRole == null || (userRole.getId()==null && userRole.getUid() == null)) {
            return null;
        }
        UserRoleQuery query = new UserRoleQuery();
        if(userRole.getId() != null){
            query.setId(userRole.getId());
        }
        if(userRole.getUid() != null){
            query.setUid(userRole.getUid());
        }
        if(userRole.getRoleId() != null){
            query.setRoleId(userRole.getRoleId());
        }
        if(userRole.getRoleStatus() != null){
            query.setRoleStatus(userRole.getRoleStatus());
        }
        return userRoleSlave.findAll(query);
    }

    /**
     * 添加用户角色
     *
     * @param userRole
     * @return
     */
    @Override
    public UserRole insert(UserRole userRole) {
        if(userRole == null){
            return null;
        }
        userRole.setId(idGlobalGenerator.getSeqId(UserRole.class));
        return userRoleMaster.save(userRole);
    }

    /**
     * 更新用户角色
     *
     * @param userRole
     * @return
     */
    @Override
    public int update(UserRole userRole) {
        if (userRole == null){
            return 0;
        }
        UserRoleQuery query = new UserRoleQuery();
        query.setId(userRole.getId());
        query.setUid(userRole.getUid());
        return userRoleMaster.update(userRole, query, "roleStatue");
    }

    /**
     * 删除用户角色
     *
     * @param userRole
     */
    @Override
    public void deleteById(UserRole userRole) {
        if (userRole == null || userRole.getId() == null){
            return;
        }
        userRoleMaster.deleteById(userRole.getId());
    }
}