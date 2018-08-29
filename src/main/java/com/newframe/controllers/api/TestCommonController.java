package com.newframe.controllers.api;

import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.services.test.TestManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:wangdong
 * @description:
 */
@RestController
@RequestMapping("/api/testCommon/")
public class TestCommonController extends BaseController {

    @Autowired
    private TestManageService testManageService;
    /**
     * 个推
     * 根据uid推
     * 推IOS设备和安卓
     * 与个人消息相关的通知
     * @param uid 用户的uid
     * @param orderId 用户的订单号
     * @param messTitle 消息的标题
     * @param messType 消息的类型：1:融资类消息，2:租机类消息，3:发货申请类的消息
     * @param messContent 消息的内容
     */
    @RequestMapping("sendMessToAllByUid")
    public JsonResult sendMessToAllByUid(Long uid, Long orderId,String messTitle, Integer messType, String messContent){

        OperationResult<Boolean> result = testManageService.sendMessToAllByUid(uid,orderId,messTitle,messType,messContent);
        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }
}
