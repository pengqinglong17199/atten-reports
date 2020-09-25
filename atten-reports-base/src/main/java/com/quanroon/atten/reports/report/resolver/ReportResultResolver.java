package com.quanroon.atten.reports.report.resolver;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.annotation.Entity;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.excepotion.AnnotationNotFoundException;
import com.quanroon.atten.reports.report.factory.ReportEntityFactory;
import com.quanroon.atten.reports.report.definition.ReportResultDefinition;
import com.quanroon.atten.reports.report.constant.DataMode;
import com.quanroon.atten.reports.report.entity.ReportEntity;
import com.quanroon.atten.reports.report.entity.ReportResult;

public class ReportResultResolver extends ReportEntityResolver {

    public ReportResultResolver(ReportEntityFactory reportEntityFactory) {
        super(reportEntityFactory);
    }

    @Override
    void resolverEntity(ReportCityCode cityCode, ReportEntity reportEntity) throws Exception {

        ReportResult reportResult = (ReportResult) reportEntity;
        Class<? extends ReportResult> resultClass = reportResult.getClass();

        // 获取数据编码格式
        Entity entityAnnotation = resultClass.getAnnotation(Entity.class);
        if(entityAnnotation == null){
            throw new AnnotationNotFoundException(resultClass.getName() + "not found @ReportEntity annotation");
        }

        for (ReportType reportType : entityAnnotation.reportType()) {

            ReportResultDefinition reportResultDefinition = new ReportResultDefinition();

            // 获取城市code
            reportResultDefinition.setCityCode(cityCode);

            DataMode dataMode = entityAnnotation.dataMode();
            reportResultDefinition.setDataFormat(dataMode);

            // 获取beanClass
            reportResultDefinition.setParam(resultClass);

            // 获取所属上报功能
            reportResultDefinition.setReportType(reportType);

            // 加入映射
            reportEntityFactory.join(reportResultDefinition);
        }
    }
}
