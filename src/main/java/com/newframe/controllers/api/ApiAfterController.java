package com.newframe.controllers.api;

import com.newframe.common.anony.Anonymous;
import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.after.request.FunderSearchDTO;
import com.newframe.dto.after.request.RoleListSearchDTO;
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

    /**
     * 后台登陆
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("login")
    public JsonResult login(String userName, String password){
        return null;
    }

    /**
     * 获取角色列表
     * @param condition
     * @return
     */
    @PostMapping("mechantList")
    public JsonResult getMechantList(RoleListSearchDTO condition){
        return null;
    }

    /**
     * 获取角色申请详细信息
     * @param roleApplyId
     * @return
     */
    @PostMapping("mechantInfo")
    public JsonResult getMechantInfo(Long roleApplyId){
        return null;
    }

    /**
     * 审核通过
     * @param uid
     * @param roleApplyId
     * @return
     */
    @PostMapping("passCheck")
    public JsonResult passCheck(Long uid, Long roleApplyId){
        return null;
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
        return null;
    }

    /**
     * 获取白名单列表
     * @param condiiton
     * @return
     */
    @PostMapping("whiteList")
    public JsonResult getWhiteList(FunderSearchDTO condiiton){
        return null;
    }

    /**
     * 获取非白名单列表
     * @return
     */
    @PostMapping("blackFunderList")
    public JsonResult getBlackFunderList(){
        return null;
    }

    /**
     * 添加资金方
     * @return
     */
    @PostMapping("addFunder")
    public JsonResult addFunder(Long uid, List<Long> funderUids){
        return null;
    }

    /**
     * 删除资金方
     * @return
     */
    @PostMapping("removeFunder")
    public JsonResult removeFunder(Long uid, Long funderUid){
        return null;
    }
}
