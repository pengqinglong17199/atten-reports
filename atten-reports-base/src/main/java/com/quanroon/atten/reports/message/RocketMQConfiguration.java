package com.quanroon.atten.reports.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@Configuration
public class RocketMQConfiguration {

    /** 打开生产者开关*/
    @Value("${quanroon.rocketmq.producer.on-off:false}")
    private boolean producerOn;

    /** 生产者名字服务器地址*/
    @Value("${quanroon.rocketmq.producer.name-server:}")
    private String producerNamesrvAddr;

    /** 生产者group*/
    @Value("${quanroon.rocketmq.producer.group-name:}")
    private String producerGroupName;

    /** 生产消息指定的topic*/
    @Value("${quanroon.rocketmq.producer.topic:}")
    private String producerTopic;

    /** 打开消费者开关*/
    @Value("${quanroon.rocketmq.consumer.on-off:false}")
    private boolean consumerOn;

    /** 是否启动虚假消费 默认false*/
    private boolean shamConsumer;

    @Value("${quanroon.rocketmq.consumer.sham-consumer:false}")
    public void setShamConsumer(boolean shamConsumer){
        if(shamConsumer){
            log.info("==> 已开启虚假消费标识");
        }
        this.shamConsumer = shamConsumer;
    }

    /** 消费者名字服务器地址*/
    @Value("${quanroon.rocketmq.consumer.name-server:}")
    private String consumerNamesrvAddr;

    /** 消费者group*/
    @Value("${quanroon.rocketmq.consumer.group-name:}")
    private String consumerGroupName;

    /** 消费topic*/
    @Value("${quanroon.rocketmq.consumer.topic:}")
    private String consumerTopic;
}
