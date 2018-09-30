package com.newframe.provider;

import com.alibaba.fastjson.JSON;
import com.newframe.configuration.rabbitmq.QueueConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息队列 - 消息提供者
 * @author:wangdong
 */
@Component
public class MessageProvider {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(MessageProvider.class);
    /**
     * 消息队列模板
     */
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Object object,String exchange,String queueConstants) {
        logger.info("写入消息队列内容：{}", JSON.toJSONString(object));
        amqpTemplate.convertAndSend(exchange, queueConstants, object);
    }

}
