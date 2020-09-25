package com.quanroon.atten.reports.exception;

/**
 * 唯一编码相关异常
 * @author 彭清龙
 * @date 2020/6/30 19:39
 */
public class RequestCodeException extends Exception {

    public RequestCodeException(){}

    public RequestCodeException(String message){
        super(message);
    }
}
