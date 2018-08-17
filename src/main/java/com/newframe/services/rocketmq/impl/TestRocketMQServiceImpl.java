package com.newframe.services.rocketmq.impl;

import com.newframe.dto.OperationResult;
import com.newframe.enums.BizErrorCode;
import com.newframe.services.rocketmq.TestRocketMQService;
import org.apache.rocketmq.spring.starter.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

        rocketMQTemplate.convertAndSend("string-topic","Hello World");

        return new OperationResult<>(true);
    }
}
