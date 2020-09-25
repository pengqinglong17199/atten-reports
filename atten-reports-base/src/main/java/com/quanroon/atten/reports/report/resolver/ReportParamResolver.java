package com.quanroon.atten.reports.report.resolver;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.annotation.InitMethod;
import com.quanroon.atten.reports.report.annotation.Entity;
import com.quanroon.atten.reports.report.annotation.Required;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.excepotion.AnnotationNotFoundException;
import com.quanroon.atten.reports.report.excepotion.NotInitMethodException;
import com.quanroon.atten.reports.report.factory.ReportEntityFactory;
import com.quanroon.atten.reports.report.definition.ReportParamDefinition;
import com.quanroon.atten.reports.report.constant.DataMode;
import com.quanroon.atten.reports.report.entity.ReportEntity;
import com.quanroon.atten.reports.report.entity.ReportParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReportParamResolver extends ReportEntityResolver {

    public ReportParamResolver(ReportEntityFactory reportParamFactory){
        super(reportParamFactory);
    }

    @Override
    public void resolverEntity(ReportCityCode cityCode, ReportEntity reportEntity) throws Exception {

        ReportParam reportParam = (ReportParam) reportEntity;
        Class<? extends ReportParam> paramClass = reportParam.getClass();

        // 获取数据编码格式
        Entity entityAnnotation = paramClass.getAnnotation(Entity.class);
        if(entityAnnotation == null){
            throw new AnnotationNotFoundException(paramClass.getName() + "not found @ReportEntity");
        }
        for (ReportType reportType : entityAnnotation.reportType()) {
            ReportParamDefinition paramDefinition = new ReportParamDefinition();

            // 获取城市code
            paramDefinition.setCityCode(cityCode);

            DataMode dataMode = entityAnnotation.dataMode();
            paramDefinition.setDataFormat(dataMode);


            // 获取beanClass
            paramDefinition.setParam(paramClass);

            // 获取初始化方法
            Method[] methods = reportParam.getClass().getMethods();
            Method initMethod = findInitMethod(methods);
            paramDefinition.setInitMethod(initMethod);

            // 获取必填与非必填字段
            Field[] fields = paramClass.getDeclaredFields();
            List<Field> requiredList = new ArrayList<>();
            List<Field> notRequiredList = new ArrayList<>();
            for (Field field : fields) {
                Required required = field.getAnnotation(Required.class);
                if(required == null){
                    notRequiredList.add(field);
                }else{
                    requiredList.add(field);
                }
            }
            paramDefinition.setRequired(requiredList);
            paramDefinition.setNotRequired(notRequiredList);

            // 获取所属上报功能
            paramDefinition.setReportType(reportType);

            // 加入映射
            reportEntityFactory.join(paramDefinition);
        }
    }


    /**
     * 查询方法数组中是否存在可供初始化的方法
     * @param methods
     * @return java.lang.reflect.Method
     * @author 彭清龙
     * @date 2020/5/22 14:06
     */
    private Method findInitMethod(Method[] methods) throws NotInitMethodException {
        for (Method method : methods) {
            boolean isInitMethod = isInitMethod(method);
            if(isInitMethod){
                return method;
            }
        }
        throw new NotInitMethodException("ReportParam not found annotations @ParamInit method");
    }

    /**
     * 判断方法中是否存在init注解
     * @param method
     * @return flag
     * @author 彭清龙
     * @date 2020/5/22 13:56
     */
    private boolean isInitMethod(Method method) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if(annotation instanceof InitMethod){
                return true;
            }
        }
        return false;
    }
}
