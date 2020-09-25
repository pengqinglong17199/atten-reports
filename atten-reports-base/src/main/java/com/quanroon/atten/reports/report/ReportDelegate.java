package com.quanroon.atten.reports.report;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.definition.BaseDefinition;
import com.quanroon.atten.reports.report.definition.ReportCommonDefinition;
import com.quanroon.atten.reports.report.definition.ReportDefinition;
import com.quanroon.atten.reports.report.entity.ReportResult;
import com.quanroon.atten.reports.report.excepotion.ReportBeanNotFoundException;
import com.quanroon.atten.reports.report.factory.BusinessFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 上报代理类
 * 由消费者基类（BaseHandler）委托此类处理上报城市的服务
 * @author 彭清龙
 * @date 2020/7/14 11:49
 */
@Slf4j
public class ReportDelegate {

    private BusinessFactory businessFactory;

    private static ReportDelegate delegate;

    private ReportDelegate(){

    }

    public static void initInstance(BusinessFactory businessFactory){
        if(delegate == null){
            delegate = new ReportDelegate();
            delegate.businessFactory = businessFactory;
        }
    }

    public static ReportDelegate getDelegate() {
        return delegate;
    }

    /**
     * 上报住建局，分发到各自城市的上报服务中
     * @param cityCode, reportType
     * @return com.quanroon.atten.reports.common.RecordType
     * @author 彭清龙
     * @date 2020/7/14 13:57
     */
    public ReportResult report(ReportCityCode cityCode, ReportType reportType, ReportMessage reportMessage) throws Exception {

            BaseDefinition base = new BaseDefinition();
            base.setCityCode(cityCode);
            base.setReportType(reportType);

            // 获取上报定义
            ReportDefinition definition = businessFactory.getDefinition(base.getKey());

            ReportCommonDefinition commonDefinition = definition.getCommonDefinition();
            Method method = commonDefinition.getMethod();
            Object obj = businessFactory.getBean(commonDefinition.getReportClass());
            ReportResult result = (ReportResult)method.invoke(obj, reportMessage);
//            // 获取请求参数实例
//            ReportParam param = businessFactory.getParamInstance(definition);
//
//            // 获取上报配置
//            ReportConfig config = definition.getConfig();
//            BaseRequest request = RequestFactory.getRequest(config);
//            String resultStr = request.call(param, definition.getParamDefinition());

            // 处理返回结果
            return result;
    }
}