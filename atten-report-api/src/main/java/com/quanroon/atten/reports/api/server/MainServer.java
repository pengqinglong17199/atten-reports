package com.quanroon.atten.reports.api.server;


import com.quanroon.atten.reports.api.server.handler.SocketInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * 主服务类
 * @author 彭清龙
 * @date 2019-12-23 下午 18:54
 */
@Component
@Slf4j
public class MainServer implements Runnable{

    private int port;
    private Channel channel;
    private ServerBootstrap serverBootstrap;
    private NioEventLoopGroup boss;
    private NioEventLoopGroup worker;
    private Thread server;

    @Autowired
    private SocketInitializer socketInitializer;

    @PostConstruct
    public void init() {
        server = new Thread(this);
        server.start();
    }

    @PreDestroy
    public void destory() {
        log.info("destroy server resources");
        if (null == channel) {
            log.info("server channel is null");
        }
        boss.shutdownGracefully();
        worker.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();

        boss = null;
        worker = null;
        channel = null;
    }

    @Override
    public void run() {
        try {
            this.startServer();
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    private void startServer() throws InterruptedException {
        serverBootstrap = new ServerBootstrap();

        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(socketInitializer);
        this.bind(serverBootstrap, 9099);
        log.info("netty服务端启动成功");

    }

    private void bind (ServerBootstrap serverBootstrap, int port) throws InterruptedException {
        ChannelFuture channelFuture = serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
               log.info("上报服务启动成功 当前端口号"+ port);
            }
        }).sync();
        this.port = port;
        channelFuture.channel().closeFuture().sync();
        channel = channelFuture.channel();
    }
}
