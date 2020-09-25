package com.quanroon.atten.reports.message;

import com.alibaba.fastjson.JSON;
import com.quanroon.atten.reports.entity.ReportMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

@Slf4j
public class AttenMessageListener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        for (MessageExt msg : msgs) {
            try {
                ReportMessageResolver.INSTANCE.handleMessage(msg);
            } catch (Exception e) {
                log.error("==> 消费出现未知异常,重试第{}次数,异常如下：", msg.getReconsumeTimes(), e);
                if(msg.getReconsumeTimes() == 1){
                    ReportMessage reportMessage = JSON.parseObject(msg.getBody(), ReportMessage.class);
                    log.error("==> 重试次数已达上限2,退出消费，消息体:{}", reportMessage.toString());
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
