package com.quanroon.atten.reports.report.resolver;

import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.excepotion.ResolverException;
import com.quanroon.atten.reports.report.factory.ReportEntityFactory;
import com.quanroon.atten.reports.report.entity.ReportEntity;

public abstract class ReportEntityResolver implements ReportResolver {

    protected ReportEntityFactory reportEntityFactory;

    public ReportEntityResolver(ReportEntityFactory reportEntityFactory){
        this.reportEntityFactory = reportEntityFactory;
    }

    @Override
    public final void resolver(ReportCityCode cityCode, Object obj) throws Exception {

        if(!(obj instanceof ReportEntity)){
            throw new ResolverException("resolver Exception bean not ReportEntity");
        }

        resolverEntity(cityCode, (ReportEntity) obj);
    }

    /**
     * 解析参数定义
     * @param reportEntity
     * @return void
     * @author 彭清龙
     * @date 2020/7/7 10:33
     */
    abstract void resolverEntity(ReportCityCode cityCode, ReportEntity reportEntity) throws Exception;
}
