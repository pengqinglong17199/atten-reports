package com.quanroon.atten.reports.api.server.entity.response;

import com.quanroon.atten.reports.api.server.entity.request.SignlogRequest;
import lombok.Data;

import java.util.Date;

import static com.quanroon.atten.reports.api.server.common.SocketCommon.SIGNLOG;


/**
* @Author: ysx
* @Date: 2020/7/2
*/
@Data
public class SignlogResponse extends PacketResponse{

    private String requestCode;

    private Integer projId;

    private Date ioTime;

    public SignlogResponse(SignlogRequest signlogRequest) {
        super(signlogRequest);
    }

    @Override
    public short getCommand() {
        return SIGNLOG;
    }

}
