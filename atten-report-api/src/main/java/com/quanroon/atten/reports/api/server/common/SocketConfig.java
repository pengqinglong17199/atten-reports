package com.quanroon.atten.reports.api.server.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 通用配置
 * @author 彭清龙
 * @date 2019-12-25 上午 11:47
 */
@Component
public class SocketConfig {

    public static Integer HEARTBEAT_OUT_TIME;

    @Value("${socket.outTime.heartbeat}")
    public void setHeartbeatOutTime(Integer heartbeatOutTime) {
        SocketConfig.HEARTBEAT_OUT_TIME = heartbeatOutTime;
    }

}
