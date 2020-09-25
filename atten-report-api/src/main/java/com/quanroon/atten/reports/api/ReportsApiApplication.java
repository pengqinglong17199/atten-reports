package com.quanroon.atten.reports.api;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement    // 事务
@EnableAspectJAutoProxy         // 打开切面
@EnableScheduling               // 打开定时任务
@ComponentScan({"com.quanroon.atten.reports.*","com.quanroon.atten.commons.*"})
@MapperScan("com.quanroon.atten.reports.dao")
public class ReportsApiApplication{

    public static void main(String[] args) {
        SpringApplication.run(ReportsApiApplication.class, args);
        System.out.println("Open+Api上报服务已启动......");
    }
}
