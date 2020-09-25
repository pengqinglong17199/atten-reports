package com.quanroon.atten.reports.api.server.handler;

import com.quanroon.atten.reports.api.server.common.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 登录校验控制器
 * @author 彭清龙
 * @date 2019-12-23 下午 20:43
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 判断是否登录
        if (!SessionUtil.hasLogin(ctx.channel())) {

            ctx.channel().close();
        } else {
            // 登录成功 此连接后续去除登录校验
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (SessionUtil.hasLogin(ctx.channel())) {
            log.info("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            log.warn("无登录验证，强制关闭连接!");
        }
    }
}