package com.newframe.controllers.api;

import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.services.rocketmq.TestRocketMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:wangdong
 * @description:
 */
@RestController
@RequestMapping("/api/testRocketMQ/")
public class TestRocketMQController extends BaseController {

    @Autowired
    private TestRocketMQService testRocketMQService;

    @RequestMapping("sendMessage")
    private JsonResult sendMessage(){

        OperationResult<Boolean> result = testRocketMQService.sendMessage();

        if (result.getSucc()) {
            return success(result.getEntity());
        }

        return error(result.getCode(), result.getMessage());
    }
}
