package com.quanroon.atten.reports.api.server.pipeline;

import com.quanroon.atten.reports.api.server.common.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 自动解码器
 * @author 彭清龙
 * @date 2019-12-02 下午 14:47
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) {

        try {
            out.add(PacketCodeC.INSTANCE.decode(in));
        } catch (Exception e) {
            ctx.channel().close();
        }
    }
}