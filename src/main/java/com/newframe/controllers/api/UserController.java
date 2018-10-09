package com.newframe.controllers.api;

import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.LoginInfo;
import com.newframe.dto.OperationResult;
import com.newframe.services.user.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:wangdong
 * @description:
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    @Autowired
    private UserManageService userManageService;

    /**
     * 获取验证码
     * @param mobile
     * @param mcodeType
     * @return
     */
    @RequestMapping("getMcode")
    public JsonResult getMcode(String mobile, Integer mcodeType) {

        OperationResult<String> result = userManageService.getMcode(mobile, mcodeType);
        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getErrorCode().getCode(), result.getErrorCode().getMessage());
    }

    /**
     * 用户的注册和登陆
     * 没有注册的就给他注册
     * 注册过的就直接给他登陆
     * @param mobile
     * @param mcode
     * @return
     */
    @RequestMapping("mobileRegisterOrLogin")
    public JsonResult mobileRegisterOrLogin(String mobile, String mcode,Integer roleType) {
        OperationResult<LoginInfo> result = userManageService.mobileRegisterOrLogin(mobile, mcode, roleType);
        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getErrorCode().getCode(), result.getErrorCode().getMessage());
    }

}
