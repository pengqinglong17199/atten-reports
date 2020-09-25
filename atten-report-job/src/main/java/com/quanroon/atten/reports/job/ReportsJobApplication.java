package com.quanroon.atten.reports.job;

import com.quanroon.atten.reports.report.annotation.EnableReport;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement    // 事务
@EnableAspectJAutoProxy         // 打开切面
@EnableScheduling               // 打开定时任务
@EnableReport                   // 打开住建局上报
@ComponentScan({"com.quanroon.atten.reports.*","com.quanroon.atten.commons.*"})
@MapperScan("com.quanroon.atten.reports.dao")
public class ReportsJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportsJobApplication.class, args);
        System.out.println("住建上报服务已启动......");
    }
}
