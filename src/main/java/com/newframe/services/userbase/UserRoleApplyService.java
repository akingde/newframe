package com.newframe.services.userbase;

import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.UserRoleApply;

import java.util.List;

/**
 *
 *  用户角色申请
 *
 * This class corresponds to the database table user_role_apply
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
public interface UserRoleApplyService {

    /**
     * 生成用户角色的申请记录
     * @param userRoleApply
     * @return
     */
    UserRoleApply insert(UserRoleApply userRoleApply);

    /**
     * 根据用户id找出申请记录，roleId选填
     * @param uid
     * @param roleId
     * @param pageSearchDTO
     * @return
     */
    List<UserRoleApply> findListByUid(Long uid, Integer roleId, PageSearchDTO pageSearchDTO);

    /**
     * 根据角色申请id去更新
     * @param userRoleApply
     * @return
     */
    int updateByRoleApplyId(UserRoleApply userRoleApply);
}