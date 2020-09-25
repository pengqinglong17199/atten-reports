package com.quanroon.atten.reports.report.excepotion;

/**
 * 实现bean找不到配置注解时抛出
 * @author 彭清龙
 * @date 2020-05-22 10:45:03
 */
public class ResolverException extends Exception {

    public ResolverException(){

    }

    public ResolverException(String message){
        super(message);
    }
}
