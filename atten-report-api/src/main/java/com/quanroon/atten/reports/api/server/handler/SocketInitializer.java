package com.quanroon.atten.reports.api.server.handler;

import com.quanroon.atten.reports.api.server.common.Spliter;
import com.quanroon.atten.reports.api.server.pipeline.PacketDecoder;
import com.quanroon.atten.reports.api.server.pipeline.PacketEncoder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 彭清龙
 * @date 2019-12-25 下午 20:25
 */
@Component
@ChannelHandler.Sharable
public class SocketInitializer extends ChannelInitializer<NioSocketChannel> {

    @Autowired private LoginHandler loginHandler;
    @Autowired private AuthHandler authHandler;
    @Autowired private HeartbeatHandler heartbeatHandler;
    @Autowired private SignlogHandler signlogHandler;

    @Override
    protected void initChannel(NioSocketChannel ch) {
        ch.pipeline().addLast(new Spliter());       // 拆包
        ch.pipeline().addLast(new PacketDecoder()); // 封装解码

        ch.pipeline().addLast(loginHandler);   // 登录

        ch.pipeline().addLast(authHandler);   // 登录校验认证
        ch.pipeline().addLast(heartbeatHandler);// 心跳
        ch.pipeline().addLast(signlogHandler);// 考勤

        ch.pipeline().addLast(new PacketEncoder()); // 编码响应
    }


}
