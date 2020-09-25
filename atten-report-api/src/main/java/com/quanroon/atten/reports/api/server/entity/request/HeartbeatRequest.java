package com.quanroon.atten.reports.api.server.entity.request;


import static com.quanroon.atten.reports.api.server.common.SocketCommon.HEARTBEAT;

/**
 * @author 彭清龙
 * @date 2019-12-23 下午 20:05
 */
public class HeartbeatRequest extends PacketRequest {

    @Override
    public short getCommand() {
        return HEARTBEAT;
    }
}
