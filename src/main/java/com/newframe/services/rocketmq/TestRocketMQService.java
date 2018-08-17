package com.newframe.services.rocketmq;

import com.newframe.dto.OperationResult;

/**
 * @author:wangdong
 * @description:测试消息队列的接口
 */
public interface TestRocketMQService {

    /**
     * 发送消息
     * @return
     */
    OperationResult<Boolean> sendMessage();
}
