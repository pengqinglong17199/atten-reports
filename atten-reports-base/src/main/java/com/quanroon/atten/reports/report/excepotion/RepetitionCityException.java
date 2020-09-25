package com.quanroon.atten.reports.report.excepotion;

/**
 * 多个bean重复使用city同一个code时抛出
 * @author 彭清龙
 * @date 2020-05-22 10:45:03
 */
public class RepetitionCityException extends Exception {

    public RepetitionCityException(){

    }

    public RepetitionCityException(String message){
        super(message);
    }
}
