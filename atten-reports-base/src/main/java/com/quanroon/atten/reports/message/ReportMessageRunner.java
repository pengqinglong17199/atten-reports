package com.quanroon.atten.reports.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@Order(1)
public class ReportMessageRunner implements ApplicationRunner, ApplicationContextAware {

    @Autowired private RocketMQTemplate rocketMQTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(BaseHandler.class, false, true);
        Map<String, BaseHandler> map = new HashMap<>();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            ReportMessageHandler annotation = bean.getClass().getAnnotation(ReportMessageHandler.class);
            if(annotation == null){
                log.error("{} not not found annotation @ReportMessageHandler", bean.getClass().getName());
                throw new Exception(bean.getClass().getName() + "not not found annotation @ReportMessageHandler");
            }
            String reportType = annotation.reportType();
            map.put(reportType, (BaseHandler)bean);
        }
        ReportMessageResolver.INSTANCE.initHandlerMapping(map);
        rocketMQTemplate.start();
    }

    // ---------------------- applicationContext ----------------------
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
