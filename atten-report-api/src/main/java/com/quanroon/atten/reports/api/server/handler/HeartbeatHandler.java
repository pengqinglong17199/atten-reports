package com.quanroon.atten.reports.api.server.handler;

import com.quanroon.atten.reports.api.server.common.SessionUtil;
import com.quanroon.atten.reports.api.server.entity.Session;
import com.quanroon.atten.reports.api.server.entity.request.HeartbeatRequest;
import com.quanroon.atten.reports.api.server.entity.response.HeartbeatResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description: 心跳
 * @Author: ysx
 * @Date: 2020/7/1
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class HeartbeatHandler extends SimpleChannelInboundHandler<HeartbeatRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartbeatRequest heartbeatRequest) throws Exception {
        // 封装响应
        HeartbeatResponse heartbeatResponse = new HeartbeatResponse(heartbeatRequest);

        // 获取session 刷新心跳
        Session session = SessionUtil.getSession(ctx.channel());
        session.setHeartbeatDate(new Date());
        heartbeatRequest.setStatus(true);

        // 写出响应包
        ctx.channel().writeAndFlush(heartbeatResponse);
    }


}
