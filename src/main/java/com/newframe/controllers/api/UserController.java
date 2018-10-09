package com.newframe.controllers.api;

import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
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

    @RequestMapping("getMcode")
    public JsonResult getMcode(String mobile, Integer mcodeType) {

        OperationResult<String> result = userManageService.getMcode(mobile, mcodeType);
        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getErrorCode().getCode(), result.getErrorCode().getMessage());
    }
}
