package com.quanroon.atten.reports.report.request;

import com.quanroon.atten.reports.report.constant.RequestMode;
import com.quanroon.atten.reports.report.constant.RequestType;
import com.quanroon.atten.reports.report.entity.ReportConfig;

/**
 * 请求简单工厂
 * @author 彭清龙
 * @date 2020/7/14 16:12
 */
public class RequestFactory {

    public static BaseRequest getRequest(ReportConfig reportConfig){
        RequestMode requestMode = reportConfig.getRequestMode();

        if(RequestMode.HTTP == requestMode){
            return new HttpRequest(reportConfig);
        }
        return null;
    }
}
