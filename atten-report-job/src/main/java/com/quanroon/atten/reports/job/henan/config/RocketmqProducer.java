/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 *//*

package com.quanroon.atten.reports.job.henan.config;

import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.reports.job.henan.entity.HeNanMessage;
import com.quanroon.atten.reports.message.RocketMQConfiguration;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

*/
/**
 * @Auther: Elvis
 * @Date: 2020-07-21 14:40
 * @Description: 提供者
 *//*

@Service
public class RocketmqProducer {

    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private RocketMQConfiguration rocketMQConfiguration;

    @PostConstruct
    public void init(){
        try{
            defaultMQProducer = new DefaultMQProducer(HeNanConfig.HENAN_GROUP);
            defaultMQProducer.setNamesrvAddr(rocketMQConfiguration.getProducerNamesrvAddr());
            defaultMQProducer.start();
        }catch (MQClientException me){
            me.printStackTrace();
        }
    }

    public void send(HeNanMessage message){
        Message message1 = new Message(HeNanConfig.HENAN_TOPIC,HeNanConfig.HENAN_TAG, JSONObject.toJSONString(message).getBytes());
        try{
            defaultMQProducer.send(message1);
        }catch (Exception me){
            me.printStackTrace();
        }
    }


}
*/
