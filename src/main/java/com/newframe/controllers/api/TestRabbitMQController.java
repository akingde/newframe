package com.newframe.controllers.api;

import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.dto.user.User;
import com.newframe.entity.rabbitmq.MessageEntity;
import com.newframe.entity.test.TestUser;
import com.newframe.provider.MessageProvider;
import com.newframe.services.test.TestManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:wangdong
 * @description:测试RabbitMQ的Controller
 */
@RestController
@RequestMapping("/api/testRabbitMQ/")
public class TestRabbitMQController extends BaseController {

    @Autowired
    private MessageProvider messageProvider;


    /**
     * 测试一下RabbitMQ是否OK
     * @param messageEntity
     * @return
     */
    @RequestMapping("index")
    private JsonResult getTestUser(MessageEntity messageEntity){

        // 将实体实例写入消息队列
        messageProvider.sendMessage(messageEntity);

        return success(true);
    }

    @RequestMapping("sendTestUser")
    private JsonResult sendTestUser(User user){

        //将实体信息写入消息队列
        messageProvider.sendMessage(user);

        return success(true);
    }
}
