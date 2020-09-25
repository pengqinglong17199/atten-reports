package com.quanroon.atten.reports.report.excepotion;

/**
 * 找不到上报bean时抛出
 * @author 彭清龙
 * @date 2020-05-22 10:45:03
 */
public class ReportBeanNotFoundException extends Exception {

    public ReportBeanNotFoundException(){

    }

    public ReportBeanNotFoundException(String message){
        super(message);
    }
}
