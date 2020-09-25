/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 *//*

package com.quanroon.atten.reports.job.henan.config;

import com.alibaba.fastjson.JSON;
import com.quanroon.atten.reports.job.henan.entity.HeNanMessage;
import com.quanroon.atten.reports.job.henan.service.HeNanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

*/
/**
 * @Auther: Elvis
 * @Date: 2020-07-21 14:45
 * @Description: 消息监听
 *//*

@Slf4j
public class ReceiveServiceListener implements MessageListenerConcurrently {

    private HeNanService heNanService;

    public ReceiveServiceListener(HeNanService heNanService){
        this.heNanService = heNanService;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        log.info("======================================>consumeMessage");
        list.forEach(messageExt -> {
            HeNanMessage heNanMessage = JSON.parseObject(messageExt.getBody(),HeNanMessage.class);
            heNanService.dealWithFindResult(heNanMessage);
        });
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
*/
