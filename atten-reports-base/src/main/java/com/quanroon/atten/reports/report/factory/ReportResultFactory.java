package com.quanroon.atten.reports.report.factory;

import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.reports.report.definition.BaseDefinition;
import com.quanroon.atten.reports.report.definition.ReportResultDefinition;
import com.quanroon.atten.reports.report.entity.ReportResult;
import com.quanroon.atten.reports.report.excepotion.NotInitMethodException;
import com.quanroon.atten.reports.report.excepotion.RepetitionCityException;
import com.quanroon.atten.reports.report.excepotion.ReportBeanNotFoundException;
import com.quanroon.atten.reports.report.entity.ReportEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReportResultFactory extends ReportEntityFactory {

    private Map<String, ReportResultDefinition> resultDefinitionMap = new HashMap<>();


    public ReportResultFactory(BusinessFactory businessFactory) {
        this.business = businessFactory;
    }

    @Override
    public ReportEntity create(String code) throws  IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    public ReportEntity create(Object obj, BaseDefinition baseDefinition) throws IllegalAccessException, InstantiationException, NotInitMethodException, InvocationTargetException, ReportBeanNotFoundException {
//        ReportResultDefinition reportResultDefinition=(ReportResultDefinition)baseDefinition;
//        Class<? extends ReportResult> param = reportResultDefinition.getParam();
//        Method initMethod = reportResultDefinition.getInitMethod();
//        ReportEntity reportEntity= (ReportEntity)JSONObject.parseObject(obj);

        return null;
    }


    @Override
    public void join(BaseDefinition reportResultDefinition) throws RepetitionCityException {
        if (resultDefinitionMap.containsKey(reportResultDefinition.getKey())) {
            throw new RepetitionCityException("result 中已存在城市code" + reportResultDefinition.getCityCode().code());
        }
        resultDefinitionMap.put(reportResultDefinition.getKey(), (ReportResultDefinition) reportResultDefinition);
    }

    @Override
    public ReportResultDefinition getDefinition(String key) {
        return resultDefinitionMap.get(key);
    }
}
