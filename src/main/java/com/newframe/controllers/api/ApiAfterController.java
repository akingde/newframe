package com.newframe.controllers.api;

import com.newframe.common.anony.Anonymous;
import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.dto.after.request.FunderSearchDTO;
import com.newframe.dto.after.request.RoleListSearchDTO;
import com.newframe.dto.after.response.*;
import com.newframe.services.after.AfterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WangBin
 */
@RestController
@RequestMapping("/inner/after/")
public class ApiAfterController extends BaseController {

    @Autowired
    private AfterService afterService;

    /**
     * 后台登陆
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("login")
    public JsonResult login(String userName, String password){
        OperationResult<UserDTO> result = afterService.login(userName, password);
        return success(result.getEntity());
    }

    /**
     * 获取角色列表
     * @param condition
     * @return
     */
    @PostMapping("mechantList")
    public JsonResult getMechantList(RoleListSearchDTO condition){
        OperationResult<RoleListDTO> result = afterService.getMechantList(condition);
        return success(result.getEntity());
    }

    /**
     * 获取角色申请详细信息
     * @param roleApplyId
     * @return
     */
    @PostMapping("mechantInfo")
    public JsonResult getMechantInfo(Long roleApplyId){
        OperationResult<RoleApplyInfoDTO> result = afterService.getMechantInfo(roleApplyId);
        return success(result.getEntity());
    }

    /**
     * 审核通过
     * @param uid
     * @param roleApplyId
     * @return
     */
    @PostMapping("passCheck")
    public JsonResult passCheck(Long uid, Long roleApplyId){
        OperationResult<Boolean> result = afterService.passCheck(uid, roleApplyId);
        if(!result.getEntity()){
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * 审核失败
     * @param uid
     * @param roleApplyId
     * @param remarks
     * @return
     */
    @PostMapping("failCheck")
    public JsonResult failCheck(Long uid, Long roleApplyId, String remarks){
        OperationResult<Boolean> result = afterService.failCheck(uid, roleApplyId, remarks);
        if(!result.getEntity()){
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * 获取白名单列表
     * @param condiiton
     * @return
     */
    @PostMapping("whiteList")
    public JsonResult getWhiteList(FunderSearchDTO condiiton){
        OperationResult<WhiteFunderListDTO> result = afterService.getWhiteList(condiiton);
        return success(result.getEntity());
    }

    /**
     * 获取非白名单列表
     * @return
     */
    @PostMapping("blackFunderList")
    public JsonResult getBlackFunderList(){
        OperationResult<List<FunderDTO>> result = afterService.getBlackFunderList();
        return success(result.getEntity());
    }

    /**
     * 添加资金方
     * @return
     */
    @PostMapping("addFunder")
    public JsonResult addFunder(Long uid, List<Long> funderUids){
        OperationResult<Boolean> result = afterService.addFunder(uid, funderUids);
        if(!result.getEntity()){
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * 删除资金方
     * @return
     */
    @PostMapping("removeFunder")
    public JsonResult removeFunder(Long uid, Long funderUid){
        OperationResult<Boolean> result = afterService.removeFunder(uid, funderUid);
        if(!result.getEntity()){
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }
}
