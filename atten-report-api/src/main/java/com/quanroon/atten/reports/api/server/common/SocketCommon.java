package com.quanroon.atten.reports.api.server.common;

/**
 * 命令常量
 * @author 彭清龙
 * @date 2019-12-23 下午 20:08
 */
public interface SocketCommon {

    short LOGIN = 12000;    // 登录
    short SIGNLOG = 12002;  // 打卡
    short HEARTBEAT = 12001;// 心跳
}
