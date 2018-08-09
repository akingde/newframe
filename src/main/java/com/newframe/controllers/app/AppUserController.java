package com.newframe.controllers.app;

import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:wangdong
 * @description:用户相关模块的Controller
 */
@RestController
@RequestMapping("/app/user/")
public class AppUserController extends BaseController {

    /**
     * 注销登陆
     *
     * @return
     */
    @RequestMapping("logout")
    public JsonResult logout() {
        return null;
    }

    /**
     * 手机登陆
     *
     * @return
     */
    @RequestMapping("mobileLogin")
    public JsonResult mobileLogin() {
        return null;
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @RequestMapping("getMcode")
    public JsonResult getMcode(String mobile, Integer codeType) {
        return null;
    }

}
