package com.quanroon.atten.reports.report.factory;


import com.quanroon.atten.reports.report.definition.BaseDefinition;
import com.quanroon.atten.reports.report.definition.ReportDefinition;
import com.quanroon.atten.reports.report.definition.ReportParamDefinition;
import com.quanroon.atten.reports.report.entity.ReportEntity;
import com.quanroon.atten.reports.report.entity.ReportParam;
import com.quanroon.atten.reports.report.excepotion.NotInitMethodException;
import com.quanroon.atten.reports.report.excepotion.RepetitionCityException;
import com.quanroon.atten.reports.report.excepotion.ReportBeanNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 上报参数工厂
 * @author 彭清龙
 * @date 2020-05-06 21:01:35
 */
public class ReportParamFactory extends ReportEntityFactory{


    private Map<String, ReportParamDefinition> paramDefinitionMap = new HashMap<>();

    public ReportParamFactory(BusinessFactory business){
        this.business = business;
    }

    @Override
    public ReportParam create(String key) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        ReportParamDefinition paramDefinition = paramDefinitionMap.get(key);
        ReportParam param = paramDefinition.getParam().newInstance();
        Method initMethod = paramDefinition.getInitMethod();
        initMethod.invoke(param);
        return param;
    }

    @Override
    public ReportEntity create(Object obj, BaseDefinition baseDefinition) throws IllegalAccessException, InstantiationException, NotInitMethodException, InvocationTargetException, ReportBeanNotFoundException {
        ReportParamDefinition paramDefinition = (ReportParamDefinition) baseDefinition;
        ReportParam param = paramDefinition.getParam().newInstance();
        Method initMethod = paramDefinition.getInitMethod();
        initMethod.invoke(param, obj);
        return null;
    }

    /**
     * 加入参数定义映射
     * @param reportParamDefinition
     * @return void
     * @author 彭清龙
     * @date 2020/7/7 11:12
     */
    @Override
    public void join(BaseDefinition reportParamDefinition) throws RepetitionCityException {
        if (paramDefinitionMap.containsKey(reportParamDefinition.getKey())) {
            throw new RepetitionCityException("param 中已存在城市code" + reportParamDefinition.getCityCode().code());
        }
        paramDefinitionMap.put(reportParamDefinition.getKey(), (ReportParamDefinition)reportParamDefinition);
    }

    @Override
    public ReportParamDefinition getDefinition(String key) {
        return paramDefinitionMap.get(key);
    }
}
