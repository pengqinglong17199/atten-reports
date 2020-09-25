package com.quanroon.atten.reports.report.excepotion;

/**
 * 必填参数为空返回
 * @auther: 郑凯林
 * @date: 2020/7/15 11:52
 */
public class NotParameterException extends Exception {

    public NotParameterException(){

    }

    public NotParameterException(String message){
        super(message);
    }
}
