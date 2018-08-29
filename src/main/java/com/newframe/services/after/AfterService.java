package com.newframe.services.after;

import com.newframe.dto.OperationResult;
import com.newframe.dto.after.request.FunderSearchDTO;
import com.newframe.dto.after.request.RoleListSearchDTO;
import com.newframe.dto.after.response.*;

import java.util.List;

/**
 * @author WangBin
 */
public interface AfterService {

    /**
     * 后台登陆
     * @param userName
     * @param password
     * @return
     */
    OperationResult<UserDTO> login(String userName, String password);

    /**
     * 获取角色列表
     * @param condition
     * @return
     */
    OperationResult<RoleListDTO> getMechantList(RoleListSearchDTO condition);

    /**
     * 获取角色申请详细信息
     * @param roleApplyId
     * @return
     */
    OperationResult<RoleApplyInfoDTO> getMechantInfo(Long roleApplyId);

    /**
     * 审核通过
     * @param uid
     * @param roleApplyId
     * @return
     */
    OperationResult<Boolean> passCheck(Long uid, Long roleApplyId);

    /**
     * 审核失败
     * @param uid
     * @param roleApplyId
     * @param remarks
     * @return
     */
    OperationResult<Boolean> failCheck(Long uid, Long roleApplyId, String remarks);

    /**
     * 获取白名单列表
     * @param condiiton
     * @return
     */
    OperationResult<WhiteFunderListDTO> getWhiteList(FunderSearchDTO condiiton);

    /**
     * 获取非白名单列表
     * @return
     */
    OperationResult<FunderDTO> getBlackFunderList();

    /**
     * 添加资金方
     * @return
     */
    OperationResult<Boolean> addFunder(Long uid, List<Long> funderUids);

    /**
     * 删除资金方
     * @return
     */
    OperationResult<Boolean> removeFunder(Long uid, Long funderUid);
}
