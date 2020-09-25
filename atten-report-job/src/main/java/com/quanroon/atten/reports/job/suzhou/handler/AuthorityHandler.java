package com.quanroon.atten.reports.job.suzhou.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.reports.job.suzhou.SuZhouClient;
import com.quanroon.atten.reports.job.suzhou.config.SuZhouConfig;
import com.quanroon.atten.reports.job.suzhou.service.NettyClientService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import static com.quanroon.atten.reports.job.suzhou.SuZhouClient.channelFuture;

/**
* netty客户端业务处理handler
*
* @Author: ysx
* @Date: 2020/8/4
*/
@Component
@ChannelHandler.Sharable
@Slf4j
public class AuthorityHandler extends SimpleChannelInboundHandler<Object> {


    @Autowired
    private NettyClientService nettyClientServiceImpl;
    @Autowired
    private SuZhouClient suZhouClient;

    /**
    * @Description: 连接到服务端后触发
    * @Param: [ctx]
    * @return: void
    * @Author: ysx
    * @Date: 2020/8/4
    */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        channelFuture.channel().writeAndFlush(SuZhouConfig.CLIENT_NAME);
    }

    /**
    * @Description: 从服务端收到消息
    * @Param: [ctx, o]
    * @return: void
    * @Author: ysx
    * @Date: 2020/8/4
    */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object o) {
        //收到消息
        ByteBuf buf = (ByteBuf) o;
        log.info(": 客户端读到数据 -> " + buf.toString(Charset.forName("utf-8")));
        //解析数据
        String msg = buf.toString(Charset.forName("utf-8"));
        JSONObject jsonObject = JSON.parseObject(msg);
        //确认从服务端收到的消息
        nettyClientServiceImpl.ackSyncMsg(jsonObject);
    }

    /**
    * @Description: 断线重连
    * @Param: [ctx]
    * @return: void
    * @Author: ysx
    * @Date: 2020/8/4
    */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if(!channelFuture.channel().isActive()){
            log.info("连接被断开...");
            //使用过程中断线重连
            final EventLoop eventLoop = channelFuture.channel().eventLoop();
            eventLoop.schedule(new Runnable() {
                @Override
                public void run() {
                    //重连
                    log.info("正在重新连接...");
                    suZhouClient.runClient();
                }
            }, 20, TimeUnit.SECONDS);
            super.channelInactive(ctx);
        }
    }

    /**
    * @Description: 异常处理
    * @Param: [ctx, cause]
    * @return: void
    * @Author: ysx
    * @Date: 2020/8/4
    */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印错误
        cause.printStackTrace();
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }

}
