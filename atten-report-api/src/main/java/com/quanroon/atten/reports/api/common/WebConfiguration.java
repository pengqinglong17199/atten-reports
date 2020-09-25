package com.quanroon.atten.reports.api.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * 拦截器配置类
 * @author 彭清龙
 * @date 2018/8/28 20:12
 * @param
 * @return
 */
@Configuration
@Primary // 优先注入
public class WebConfiguration implements WebMvcConfigurer {

    //项目根路径
    @Value("${server.adminPath:}")
    private String projectPath;

    @Bean
    ReportApiInterceptor getReportApiInterceptor(){
        return new ReportApiInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加openApi鉴权拦截器 addPathPatterns() 添加拦截路径
        registry.addInterceptor(getReportApiInterceptor())
                .addPathPatterns(getIncludePathPatterns())
                .excludePathPatterns(projectPath+"/sys/token/company");
    }

    /**
     * 需要认证的路径
     * @return
     */
    private ArrayList<String> getIncludePathPatterns() {
        ArrayList<String> list = new ArrayList<>();
        String[] urls = {
                projectPath+"/*/**"
        };
        Collections.addAll(list, urls);
        return list;
    }
}
