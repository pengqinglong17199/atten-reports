package com.quanroon.atten.reports.report.excepotion;

/**
 * 上报功能方法不规范时抛出
 * @author 彭清龙
 * @date 2020-05-22 10:45:03
 */
public class NoNormMethodException extends Exception {

    public NoNormMethodException(){

    }

    public NoNormMethodException(String message){
        super(message);
    }
}
