/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 *//*

package com.quanroon.atten.reports.job.henan.config;

import com.quanroon.atten.reports.job.henan.service.HeNanService;
import com.quanroon.atten.reports.message.RocketMQConfiguration;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

*/
/**
 * @Auther: Elvis
 * @Date: 2020-07-22 10:54
 * @Description: 消费者
 *//*

@Service
public class RocketmqCosumer {

    @Autowired
    private HeNanService heNanService;

    @Autowired
    private RocketMQConfiguration rocketMQConfiguration;

    @PostConstruct
    public void init(){
        try{
            DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(HeNanConfig.HENAN_GROUP);
            defaultMQPushConsumer.setNamesrvAddr(rocketMQConfiguration.getConsumerNamesrvAddr());
            defaultMQPushConsumer.subscribe(HeNanConfig.HENAN_TOPIC,HeNanConfig.HENAN_TAG);
            defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            defaultMQPushConsumer.registerMessageListener(new ReceiveServiceListener(heNanService));
            defaultMQPushConsumer.start();
        }catch (MQClientException me){
            me.printStackTrace();
        }

    }
}
*/
