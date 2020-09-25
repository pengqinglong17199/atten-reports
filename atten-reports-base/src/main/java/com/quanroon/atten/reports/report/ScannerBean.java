package com.quanroon.atten.reports.report;

import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.entity.*;
import com.quanroon.atten.reports.report.excepotion.AnnotationNotFoundException;
import com.quanroon.atten.reports.report.excepotion.RepetitionCityException;
import com.quanroon.atten.reports.report.excepotion.ReportBeanNotFoundException;
import com.quanroon.atten.reports.report.factory.*;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.resolver.ReportResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

/**
 * 扫描并初始化bean
 * @author 彭清龙
 * @description: 扫描上报bean
 * @date 2020-04-27 20:37:28
 */
//@Component
@Slf4j
@Order(0)
public class ScannerBean implements ApplicationRunner {


    @Autowired private BusinessFactory businessFactory;
    @Autowired private ReportResolver reportResolver;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 初始化上报配置
        initConfig();

        // 初始化上报参数定义
        initRequestParam();

        // 初始化返回参数定义
        initResult();

        // 完成上报功能映射
        initCommon();

        // 初始化上报功能定义
        initReportDefinition();

        log.info("report server auto injection of finish");
    }

    /**
     * 完成对上报整体业务的定义
     * @return void
     * @author 彭清龙
     * @date 2020/7/7 17:07
     */
    private void initReportDefinition() throws ReportBeanNotFoundException {
        businessFactory.initReportDefinition();
    }

    /**
     * 初始化上报功能
     * @return void
     * @author 彭清龙
     * @date 2020/7/7 17:07
     */
    private void initCommon() throws Exception {
        String[] beanNames = businessFactory.getBeanNamesForType(ReportService.class);
        resolverBeanNames(beanNames);
    }

    /**
     * 初始化上报返回结果
     * @return void
     * @author 彭清龙
     * @date 2020/7/7 17:06
     */
    private void initResult() throws Exception {
        String[] beanNames = businessFactory.getBeanNamesForType(ReportResult.class);
        resolverBeanNames(beanNames);
    }

    /**
     * 初始化上报请求参数
     * @return void
     * @author 彭清龙
     * @date 2020/7/7 17:06
     */
    private void initRequestParam() throws Exception {
        String[] beanNames = businessFactory.getBeanNamesForType(ReportParam.class);
        resolverBeanNames(beanNames);
    }

    /**
     * 初始化上报配置实例
     * @return void
     * @author 彭清龙
     * @date 2020/7/7 17:06
     */
    private void initConfig() throws AnnotationNotFoundException, IllegalAccessException, InstantiationException, RepetitionCityException {
        String[] beanNames = businessFactory.getBeanNamesForType(ReportConfig.class);
        for (String beanName : beanNames) {
            ReportConfig bean = businessFactory.getBean(beanName, ReportConfig.class);
            City city = bean.getClass().getAnnotation(City.class);
            if(city == null){
                throw new AnnotationNotFoundException(bean.getClass().getName() + "找不到@City注解");
            }
            ReportCityCode value = city.value();
            businessFactory.joinConfig(value.code(), bean);
        }
    }

    /**
     * 使用解析器对bean进行解析并定义
     * @param beanNames
     * @return void
     * @author 彭清龙
     * @date 2020/7/7 17:05
     */
    private void resolverBeanNames(String[] beanNames) throws Exception {
        for (String beanName : beanNames) {
            Object bean = businessFactory.getBean(beanName);
            City city = bean.getClass().getAnnotation(City.class);
            if(city == null){
                throw new AnnotationNotFoundException(bean.getClass().getName() + "找不到@City注解");
            }
            ReportCityCode value = city.value();
            reportResolver.resolver(value, bean);
        }
    }
}
