package com.quanroon.atten.reports.report.excepotion;

/**
 * 当项目没有上报城市时抛出
 * @author 彭清龙
 * @date 2020/7/14 14:09
 */
public class NotReportCityException  extends Exception {

    public NotReportCityException(){

    }

    public NotReportCityException(String message){
        super(message);
    }
}
