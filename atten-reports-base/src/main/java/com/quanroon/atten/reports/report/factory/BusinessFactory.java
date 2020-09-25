package com.quanroon.atten.reports.report.factory;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.ReportDelegate;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.definition.ReportCommonDefinition;
import com.quanroon.atten.reports.report.definition.ReportDefinition;
import com.quanroon.atten.reports.report.definition.ReportParamDefinition;
import com.quanroon.atten.reports.report.entity.ReportConfig;
import com.quanroon.atten.reports.report.entity.ReportParam;
import com.quanroon.atten.reports.report.entity.ReportResult;
import com.quanroon.atten.reports.report.entity.ReportService;
import com.quanroon.atten.reports.report.excepotion.NotInitMethodException;
import com.quanroon.atten.reports.report.excepotion.NotParameterException;
import com.quanroon.atten.reports.report.excepotion.RepetitionCityException;
import com.quanroon.atten.reports.report.excepotion.ReportBeanNotFoundException;
import com.quanroon.atten.reports.report.resolver.ReportCommonResolver;
import com.quanroon.atten.reports.report.resolver.ReportParamResolver;
import com.quanroon.atten.reports.report.resolver.ReportResolver;
import com.quanroon.atten.reports.report.resolver.ReportResultResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class BusinessFactory implements ReportResolver, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private Map<String, ReportDefinition> definitionMap = new ConcurrentHashMap<>();
    private Map<String, ReportCommonDefinition> commonDefinitionMap = new HashMap<>();

    private ReportEntityFactory paramFactory;

    private ReportEntityFactory resultFactory;

    private ReportConfigFactory configFactory;

    private ReportResolver paramResolver;

    private ReportResolver resultResolver;

    private ReportResolver commonResolver;

    public BusinessFactory(){
        // 创建工厂
        paramFactory = new ReportParamFactory(this);
        resultFactory = new ReportResultFactory(this);
        configFactory = new ReportConfigFactory(this);

        // 创建解析器
        paramResolver = new ReportParamResolver(paramFactory);
        resultResolver = new ReportResultResolver(resultFactory);
        commonResolver = new ReportCommonResolver(this);

        // 上报代理器
        ReportDelegate.initInstance(this);
    }



    /**
     * 获取通过地区编码获取ReportDefinition
     * @param key
     * @return com.quanroon.saas.report.base.factory.ReportDefinition
     * @author 彭清龙
     * @date 2020/5/22 14:10
     */
    public ReportDefinition getDefinition(String key) throws ReportBeanNotFoundException {
        ReportDefinition reportDefinition = definitionMap.get(key);
        if(reportDefinition == null){
            throw new ReportBeanNotFoundException("ReportDefinition not found [" + key + "] unregistered");
        }
        return reportDefinition;
    }

    /**
     * 解析上报bean
     * @param cityCode, obj
     * @return void
     * @author 彭清龙
     * @date 2020/7/7 17:04
     */
    @Override
    public void resolver(ReportCityCode cityCode, Object obj) throws Exception {
        if(obj instanceof ReportParam){

            paramResolver.resolver(cityCode, obj);

        }else if(obj instanceof ReportResult){

            resultResolver.resolver(cityCode, obj);

        }else if(obj instanceof ReportService){

            commonResolver.resolver(cityCode, obj);

        }
    }

    /**
     * 获取config
     * @param code
     * @return com.quanroon.atten.reports.report.entity.ReportConfig
     * @author 彭清龙
     * @date 2020/7/7 17:00
     */
    public ReportConfig getConfig(String code) {
        return configFactory.getConfig(code);
    }

    /**
     * 新加入配置实例
     * @param code, bean
     * @return void
     * @author 彭清龙
     * @date 2020/7/7 17:01
     */
    public void joinConfig(String code, ReportConfig bean) throws RepetitionCityException {
        configFactory.join(code, bean);
    }

    /**
     * 新加入上报功能定义
     * @param reportCommonDefinition
     * @return void
     * @author 彭清龙
     * @date 2020/7/10 11:29
     */
    public void joinCommon(ReportCommonDefinition reportCommonDefinition) throws RepetitionCityException {
        if (commonDefinitionMap.containsKey(reportCommonDefinition.getKey())) {
            throw new RepetitionCityException("城市 ["+reportCommonDefinition.getCityCode().code()+"] 中存在多个相同的上报功能！");
        }
        commonDefinitionMap.put(reportCommonDefinition.getKey(), reportCommonDefinition);
    }

    /**
     * 初始化上报功能定义
     * @author 彭清龙
     * @date 2020/7/10 12:00
     */
    public void initReportDefinition() throws ReportBeanNotFoundException {
        for (ReportCityCode cityCode : ReportCityCode.values()) {
            String code = cityCode.code();
            ReportConfig config = getConfig(code);
            if(config == null){
                throw new ReportBeanNotFoundException("城市 [" + code + "] 找不到ReportConfig!");
            }
            for (ReportType reportType : ReportType.values()) {
                ReportDefinition reportDefinition = new ReportDefinition();
                reportDefinition.setConfig(config);
                reportDefinition.setCityCode(cityCode);
                reportDefinition.setReportType(reportType);

                // 获取功能定义
                if (!commonDefinitionMap.containsKey(reportDefinition.getKey())){
                    continue;
                }
                ReportCommonDefinition commonDefinition = commonDefinitionMap.get(reportDefinition.getKey());
                reportDefinition.setCommonDefinition(commonDefinition);

//                // 获取参数定义
//                ReportParamDefinition paramDefinition = (ReportParamDefinition) paramFactory.getDefinition(reportDefinition.getKey());
//                if(paramDefinition == null){
//                    throw new ReportBeanNotFoundException("城市 [" + code + "] 上报功能 [" + reportType + "]找不到ReportParam!");
//                }
//                reportDefinition.setParamDefinition(paramDefinition);
//
//                // 获取响应定义
//                ReportResultDefinition resultDefinition = (ReportResultDefinition) resultFactory.getDefinition(reportDefinition.getKey());
//                if(resultDefinition == null){
//                    throw new ReportBeanNotFoundException("城市 [" + code + "] 上报功能 [" + reportType + "]找不到ReportResult!");
//                }
//                reportDefinition.setResultDefinition(resultDefinition);
                definitionMap.put(reportDefinition.getKey(), reportDefinition);
            }
        }
    }

    public void init() {

    }

    /**
     * 获取上报参数实体实例
     * @param definition
     * @return void
     * @author 彭清龙
     * @date 2020/7/14 14:35
     */
    public ReportParam getParamInstance(ReportDefinition definition) throws InstantiationException, IllegalAccessException, InvocationTargetException, NotParameterException {
        ReportParam reportParam = (ReportParam)paramFactory.create(definition.getKey());
        ReportParamDefinition paramDefinition = definition.getParamDefinition();
        List<Field> required = paramDefinition.getRequired();
        for (Field field : required) {
            Object o = field.get(reportParam);
            if(o == null){
                throw new NotParameterException(field.getName()+"字段为空");
            }
        }
        return  reportParam;
    }

    /**
     * 获取上报响应实体实例
     * @param resultStr, definition
     * @return com.quanroon.atten.reports.report.entity.ReportResult
     * @author 彭清龙
     * @date 2020/7/15 14:32
     */
    public ReportResult getResultInstance(String resultStr, ReportDefinition definition) throws InvocationTargetException, NotInitMethodException, InstantiationException, ReportBeanNotFoundException, IllegalAccessException {
        ReportResult reportResult=(ReportResult)resultFactory.create(resultStr, definition.getResultDefinition());
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Object getBean(Class<? extends ReportService> reportClass) {
        return applicationContext.getBean(reportClass);
    }

    public String[] getBeanNamesForType(Class<?> clazz) {
        return applicationContext.getBeanNamesForType(clazz);
    }

    public Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }
}
