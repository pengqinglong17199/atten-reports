package com.quanroon.atten.reports.api.server.entity.response;


import com.quanroon.atten.reports.api.server.entity.Packet;
import com.quanroon.atten.reports.api.server.entity.request.PacketRequest;
import lombok.Data;

/**
 * 响应父类
 * @author 彭清龙
 * @date 2019-12-23 下午 20:39
 */
@Data
public abstract class PacketResponse extends Packet {

    protected String message;
    private Integer id;

    public PacketResponse(PacketRequest packetRequest){
        this.setVersion(packetRequest.getVersion());
        this.setSessionId(packetRequest.getSessionId());
        this.setSerializer(packetRequest.getSerializer());
    }
}
