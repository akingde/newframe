package com.newframe.services.userbase;

import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.UserRoleApply;
import org.springframework.data.domain.Page;

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
     * 根据角色申请id和uid找出指定的记录
     * @param roleApplyId
     * @param uid 非必选
     * @return
     */
    UserRoleApply findOne(Long roleApplyId, Long uid);

    /**
     * 根据用户id找出申请记录，roleId选填
     * @param uid
     * @param roleId
     * @param pageSearchDTO
     * @return
     */
    Page<UserRoleApply> findListByUid(Long uid, Integer roleId, PageSearchDTO pageSearchDTO);

    /**
     * 根据角色申请id去更新
     * @param userRoleApply
     * @return
     */
    int updateByRoleApplyId(UserRoleApply userRoleApply);

    /**
     * 根据uid获取角色申请列表
     * @param uid
     * @return
     */
    List<UserRoleApply> findApplyList(Long uid);
}