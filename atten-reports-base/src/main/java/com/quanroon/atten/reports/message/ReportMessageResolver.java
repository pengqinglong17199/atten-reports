package com.quanroon.atten.reports.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.exception.RequestCodeException;
import com.quanroon.atten.reports.report.excepotion.NotParameterException;
import com.quanroon.atten.reports.report.excepotion.NotReportCityException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.HashMap;
import java.util.Map;

/**
 * 上报消息处理解析器
 * @author 彭清龙
 * @date 2020/7/2 19:50
 */
@Slf4j
public class ReportMessageResolver {

    /** 消息功能处理mapping*/
    private Map<String, BaseHandler> handlerMapping;

    /** 单例化*/
    public static final ReportMessageResolver INSTANCE = new ReportMessageResolver();

    private ReportMessageResolver(){}

    /**
     * 初始化handlerMapping
     * @author 彭清龙
     * @date 2020/7/2 20:22
     */
    public void initHandlerMapping(Map<String, BaseHandler> handlerMapping){
        if(this.handlerMapping != null){
            return;
        }
        this.handlerMapping = handlerMapping;
    }

    /**
     * 消息委派分发
     * @param messageExt
     * @return void
     * @author 彭清龙
     * @date 2020/7/2 20:14
     */
    public void handleMessage(MessageExt messageExt)  {
        byte[] body = messageExt.getBody();
        ReportMessage reportMessage = JSON.parseObject(body, ReportMessage.class);
        String tags = messageExt.getTags();
        BaseHandler baseHandler = handlerMapping.get(tags);
        try {
            baseHandler.resolver(reportMessage);
        }catch (RequestCodeException e){
            log.error("requestCodeException：", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NotReportCityException e) {
            e.printStackTrace();
        } catch (NotParameterException e) {
            e.printStackTrace();
        } catch (BusinessException e){
            log.error("businessException：", e);
        }
    }
}
