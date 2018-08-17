package com.newframe.services.rocketmq.impl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.rocketmq.OrderPaidEvent;
import com.newframe.enums.BizErrorCode;
import com.newframe.services.rocketmq.TestRocketMQService;
import org.apache.rocketmq.spring.starter.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author:wangdong
 * @description:
 */
@Service
public class TestRocketMQServiceImpl implements TestRocketMQService{

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    /**
     * 发送消息
     *
     * @return
     */
    @Override
    public OperationResult<Boolean> sendMessage() {

        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
        rocketMQTemplate.convertAndSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")));

        return new OperationResult<>(true);
    }
}
