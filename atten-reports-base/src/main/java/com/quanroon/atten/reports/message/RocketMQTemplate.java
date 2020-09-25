package com.quanroon.atten.reports.message;

import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.entity.base.CodeEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class RocketMQTemplate {

    private DefaultMQProducer producer;
    private DefaultMQPushConsumer consumer;
    private boolean startFlag = true;
    @Autowired private RocketMQConfiguration rocketMQConfiguration;

    @PostConstruct
    public void init() throws MQClientException {
        // 是否打开生产者开关
        if(producer == null){
            if(rocketMQConfiguration.isProducerOn()){
                producer = new DefaultMQProducer(rocketMQConfiguration.getProducerGroupName());
                producer.setNamesrvAddr(rocketMQConfiguration.getProducerNamesrvAddr());
            }
        }

        // 是否打开消费者开关
        if(consumer == null){
            if(rocketMQConfiguration.isConsumerOn()){
                consumer = new DefaultMQPushConsumer(rocketMQConfiguration.getConsumerGroupName());
                consumer.setNamesrvAddr(rocketMQConfiguration.getConsumerNamesrvAddr());
                consumer.subscribe(rocketMQConfiguration.getConsumerTopic(), "*");
                consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
                consumer.setConsumeTimestamp("20181109221800");
                consumer.registerMessageListener(new AttenMessageListener());
            }
        }
    }

    public void start() throws MQClientException {
        if(startFlag){
            startFlag = false;

            if(producer != null){
                producer.start();
                log.info("rocketmq producer start success");
            }

            if(consumer != null){
                consumer.start();
                log.info("rocketmq consumer start success, subscribe of topic " + consumer.getSubscription());
            }
        }
    }

    /**
     * 虚假上报开关
     * @param
     * @author quanroon.ysq
     * @date 2020.08.06 12:11
     * @return boolean
     */
    public boolean isShamConsumer(){
        return rocketMQConfiguration.isShamConsumer();
    }
    /**
     * 推送消息队列
     * @param codeEntity, tags, reportMessage
     * @return void
     * @author 彭清龙
     * @date 2020/7/2 15:27
     */
    public void send(CodeEntity codeEntity, String tags, ReportMessage reportMessage) throws Exception {
        try {
            Message message = new Message(rocketMQConfiguration.getProducerTopic(), tags,
                    reportMessage.getRequestCode(), JSONObject.toJSONBytes(reportMessage));
            message.setDelayTimeLevel(2);
            // todo 没有注释
            SendResult send = producer.send(message,(mqs, msg, arg)->{
                int index = arg.hashCode();
                if(index < 0)
                    index = Math.abs(index);
                index = index % mqs.size();
                return mqs.get(index);
            },codeEntity.getProjId());

            log.debug(send.toString());
        }catch (Exception e){
            log.error("消息发送失败", e);
            throw e;
        }
    }
}
