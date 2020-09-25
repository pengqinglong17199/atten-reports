package com.quanroon.atten.reports.report.excepotion;

/**
 * 构建实体时需要有初始化方法 但是没有时抛出
 * @author 彭清龙
 * @date 2020-05-22 14:00:49
 */
public class NotInitMethodException extends Exception {

    public NotInitMethodException(){

    }

    public NotInitMethodException(String message){
        super(message);
    }
}
