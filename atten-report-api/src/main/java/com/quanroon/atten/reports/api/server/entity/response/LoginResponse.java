package com.quanroon.atten.reports.api.server.entity.response;

import com.quanroon.atten.reports.api.server.entity.request.LoginRequest;
import lombok.Data;

import static com.quanroon.atten.reports.api.server.common.SocketCommon.LOGIN;

@Data
public class LoginResponse extends PacketResponse{

    public LoginResponse(LoginRequest loginRequest) {
        super(loginRequest);
        projId = loginRequest.getProjId();
    }

    private Integer projId;

    @Override
    public short getCommand() {
        return LOGIN;
    }

}
