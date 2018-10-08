package com.newframe.configuration.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置类
 * @author wangdong
 */
@Configuration
public class MessageRabbitMqConfiguration {
    /**
     * 交换配置
     *
     * @return
     */
    @Bean
    public DirectExchange messageDirectExchange() {
        return (DirectExchange) ExchangeBuilder.directExchange(QueueConstants.MESSAGE_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public DirectExchange messageAliExchange() {
        return (DirectExchange) ExchangeBuilder.directExchange(QueueConstants.MESSAGE_ALIEXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * 消息队列声明
     *
     * @return
     */
    @Bean
    public Queue messageQueue() {
        return QueueBuilder.durable(QueueConstants.MESSAGE_QUEUE_NAME)
                .build();
    }

    /**
     * 消息绑定
     *
     * @return
     */
    @Bean
    public Binding messageBinding() {
        return BindingBuilder.bind(messageQueue())
                .to(messageDirectExchange())
                .with(QueueConstants.MESSAGE_ROUTE_KEY);
    }

    /**
     * 消息队列声明(发送验证码)
     *
     * @return
     */
    @Bean
    public Queue sendVcodeMessageQueue() {
        return QueueBuilder.durable(QueueConstants.MESSAGE_QUEUE_SENDCODE)
                .build();
    }

    /**
     * 消息绑定
     *
     * @return
     */
    @Bean
    public Binding sendVcodeMessageBinding() {
        return BindingBuilder.bind(sendVcodeMessageQueue())
                .to(messageAliExchange())
                .with(QueueConstants.MESSAGE_ROUTE_SENDCODE);
    }


}
