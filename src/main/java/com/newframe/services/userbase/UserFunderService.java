package com.newframe.services.userbase;

import com.newframe.dto.after.request.FunderSearchDTO;
import com.newframe.entity.user.UserFunder;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 *  资金方
 *
 * This class corresponds to the database table user_funder
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
public interface UserFunderService {

    /**
     * 查询
     * @param uid
     * @return
     */
    UserFunder findOne(Long uid);

    /**
     * 获取所有的非白名单资金方
     * @return
     */
    List<UserFunder> findAll();

    /**
     * 根据uid获取在白名单中的资金方
     * @param funderUid
     * @return
     */
    List<UserFunder> findAll(List<Long> funderUid);

    /**
     * 获取所有白名单的资金方
     * @param condition
     * @return
     */
    Page<UserFunder> findAll(FunderSearchDTO condition);

    /**
     * 添加
     * @param userFunder
     * @return
     */
    UserFunder insert(UserFunder userFunder);

    /**
     * 更新
     * @param userFunder
     * @return
     */
    int update(UserFunder userFunder);

    /**
     * 将指定的资金方添加/删除到白名单
     *
     * @param isWhite
     * @param funderUid
     * @return
     */
    int update(Boolean isWhite, List<Long> funderUid);

    /**
     * 将指定的资金方添加/删除到白名单
     *
     * @param isWhite
     * @param funderUid
     * @return
     */
    int update(Boolean isWhite, Long funderUid);

    /**
     * 删除
     * @param uid
     */
    void delete(Long uid);
}