package com.quanroon.atten.reports.report.resolver;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.report.annotation.ReportMethod;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.definition.ReportCommonDefinition;
import com.quanroon.atten.reports.report.entity.ReportResult;
import com.quanroon.atten.reports.report.entity.ReportService;
import com.quanroon.atten.reports.report.excepotion.NoNormMethodException;
import com.quanroon.atten.reports.report.factory.BusinessFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 上报功能解析器
 * @author 彭清龙
 * @date 2020/7/7 16:57
 */
public class ReportCommonResolver implements ReportResolver {

    protected BusinessFactory businessFactory;

    public ReportCommonResolver(BusinessFactory businessFactory){
        this.businessFactory = businessFactory;
    }

    @Override
    public void resolver(ReportCityCode cityCode, Object obj) throws Exception {
        ReportService reportService = (ReportService) obj;
        Method[] methods = reportService.getClass().getMethods();
        for (Method method : methods) {

            // 校验注解
            ReportMethod annotation = method.getAnnotation(ReportMethod.class);
            if (annotation == null) {
                continue;
            }

            // 校验参数数量
            Parameter[] parameters = method.getParameters();
            if (parameters.length != 1) {
                throw new NoNormMethodException(reportService.getClass() + "." +method.getName()+"参数应该是 ReportMessage.class");
            }

            // 校验参数类型
            Class<?> type = parameters[0].getType();
            if (type != ReportMessage.class) {
                throw new NoNormMethodException(reportService.getClass() + "." +method.getName()+"参数应该是 ReportMessage.class");
            }

            // 校验方法返回
            Class<?> returnType = method.getReturnType();
            if (!ReportResult.class.isAssignableFrom(returnType)) {
                throw new NoNormMethodException(reportService.getClass() + "." +method.getName()+"返回值应该是 ReportResult.class");
            }

            // 获取注解
            for (ReportType reportType : annotation.value()) {
                // 生成上报功能定义
                ReportCommonDefinition reportCommonDefinition = new ReportCommonDefinition();
                reportCommonDefinition.setReportClass(reportService.getClass());
                reportCommonDefinition.setMethod(method);
                reportCommonDefinition.setCityCode(cityCode);
                reportCommonDefinition.setReportType(reportType);

                businessFactory.joinCommon(reportCommonDefinition);
            }
        }
    }
}
