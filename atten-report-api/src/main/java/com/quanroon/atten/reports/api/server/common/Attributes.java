package com.quanroon.atten.reports.api.server.common;


import com.quanroon.atten.reports.api.server.entity.Session;
import io.netty.util.AttributeKey;


/**
 * 连接通道属性汇总
 * @author 彭清龙
 * @date 2019-12-23 下午 20:08
 */
public interface Attributes {

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}