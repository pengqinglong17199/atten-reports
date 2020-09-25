package com.quanroon.atten.reports.job.suzhou;

import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.reports.job.suzhou.common.SyncFuture;
import com.quanroon.atten.reports.job.suzhou.config.SuZhouConfig;
import com.quanroon.atten.reports.job.suzhou.handler.AuthorityHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
* netty客户端
*
* @Author: ysx
* @Date: 2020/8/4
*/
@Component
@Slf4j
public class SuZhouClient {

    public static ChannelFuture channelFuture;

    @Autowired
    private AuthorityHandler authorityHandler;

    /** netty服务端ip地址*/
    @Value("${suzhou.url}")
    public String IP;

    /** netty服务端端口号*/
    @Value("${suzhou.port}")
    public Integer PORT;

    /**
    * @Description: netty客户端启动入口
    * @Param: []
    * @return: void
    * @Author: ysx
    * @Date: 2020/8/4
    */
    @PostConstruct
    public void runClient() {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    public void initChannel(NioSocketChannel ch) {
                        //服务端提供的拆包器
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        ch.pipeline().addLast(new LengthFieldPrepender(4));
                        //业务处理handler
                        ch.pipeline().addLast(authorityHandler);
                    }
                });
        // 4.建立连接
        bootstrap.connect(IP, PORT).addListener(future -> {
            if (future.isSuccess()) {
                channelFuture = (ChannelFuture) future;
                log.info("==> 宿州上报,连接Netty服务端成功!");
            } else {
                log.info("==> 宿州上报,连接Netty服务端失败，进行断线重连...");
                final EventLoop loop = channelFuture.channel().eventLoop();
                loop.schedule(new Runnable() {
                    @Override
                    public void run() {
                        log.info("==> 宿州上报,连接正在重试...");
                        runClient();
                    }
                }, 20, TimeUnit.SECONDS);
            }
        });
    }

    /**
    * @Description: 消息发送
    * @Param: [byteBuf, syncFuture]
    * @return: com.alibaba.fastjson.JSONObject
    * @Author: ysx
    * @Date: 2020/8/4
    */
    public JSONObject sendSyncMsg(ByteBuf byteBuf, SyncFuture<JSONObject> syncFuture) {
        JSONObject result = null;
        try {
            new Thread( () -> {
                ChannelFuture future = channelFuture.channel().writeAndFlush(byteBuf);
                future.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()) {
                            log.info("消息发送成功");
                        } else {
                            log.info("消息发送失败");
                        }
                    }
                });
            } ).start();

            // 等待 8 秒
            result = syncFuture.get(8, TimeUnit.SECONDS);
            log.info("发送结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

}