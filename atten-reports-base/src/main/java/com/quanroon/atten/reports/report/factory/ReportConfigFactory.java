package com.quanroon.atten.reports.report.factory;


import com.quanroon.atten.reports.report.excepotion.RepetitionCityException;
import com.quanroon.atten.reports.report.entity.ReportConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 上报配置工厂
 * @author 彭清龙
 * @date 2020-05-06 20:57:16
 */
public class ReportConfigFactory{

    private Map<String, ReportConfig> configMap = new HashMap<>();

    protected BusinessFactory business;

    public ReportConfigFactory(BusinessFactory businessFactory){
        this.business = businessFactory;
    }

    /**
     * 通过class生产实例
     * @param clazz
     * @return com.quanroon.atten.reports.report.entity.ReportConfig
     * @author 彭清龙
     * @date 2020/7/7 9:01
     */
    public ReportConfig create(Class<? extends ReportConfig> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

    /**
     * 获取上报配置实例
     * @param code
     * @return com.quanroon.atten.reports.report.entity.ReportConfig
     * @author 彭清龙
     * @date 2020/7/7 9:01
     */
    public ReportConfig getConfig(String code) {
        return configMap.get(code);
    }

    /**
     * 加入映射
     * @param code, bean
     * @return void
     * @author 彭清龙
     * @date 2020/7/7 9:44
     */
    public void join(String code, ReportConfig bean) throws RepetitionCityException {
        if (configMap.containsKey(code)) {
            throw new RepetitionCityException("config 中已存在城市code"+ code);
        }
        configMap.put(code, bean);
    }
}
