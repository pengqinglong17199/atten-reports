package com.quanroon.atten.reports.report.constant;

/**
 * 鉴权方式 请求住建局 实际的鉴权方式
 * @author 彭清龙
 * @date 2020-04-27 20:42:35
 */
public enum AuthenticationMode {

    /** 鉴权在请求头中*/
    HEADER,
    /** 鉴权拼接在url后面*/
    URL,
    /** 鉴权在body中*/
    BODY;
}
