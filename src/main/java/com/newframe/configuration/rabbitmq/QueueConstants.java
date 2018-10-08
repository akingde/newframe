package com.newframe.configuration.rabbitmq;

/**
 * 队列常量配置
 * @author wangdong
 */
public interface QueueConstants {
    /**
     * 消息交换
     */
    String MESSAGE_EXCHANGE = "message.direct.exchange";

    String MESSAGE_ALIEXCHANGE="message.ali.exchange";

    //队列名称要和路由键绑定一一对应
    /**
     * 消息队列名称
     */
    String MESSAGE_QUEUE_NAME = "message.queue";
    /**
     * 消息路由键
     */
    String MESSAGE_ROUTE_KEY = "message.send";



    //发送验证码
    /**
     * 发送验证码的队列名称
     */
    String MESSAGE_QUEUE_SENDCODE = "message.queuesendcode";

    /**
     * 发送验证码的消息路由键
     */
    String MESSAGE_ROUTE_SENDCODE = "message.sendcode";
}
