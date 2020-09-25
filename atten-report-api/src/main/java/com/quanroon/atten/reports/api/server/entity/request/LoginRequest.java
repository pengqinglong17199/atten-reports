package com.quanroon.atten.reports.api.server.entity.request;

import com.quanroon.atten.reports.api.server.annotation.Length;
import lombok.Data;

import static com.quanroon.atten.reports.api.server.common.SocketCommon.LOGIN;

@Data
public class LoginRequest extends PacketRequest{
    //项目id
    @Length(value = 4,sort = 1)
    private Integer projId;
    //项目key
    @Length(value = 32,sort = 2)
    private String authKey;
    //校验和
    @Length(value = 1,sort = 3)
    private Byte xor;

    @Override
    public short getCommand() {
        return LOGIN;
    }

}
