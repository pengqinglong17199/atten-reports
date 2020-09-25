package com.quanroon.atten.reports.api.server.pipeline;


import com.quanroon.atten.reports.api.server.common.PacketCodeC;
import com.quanroon.atten.reports.api.server.entity.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * 自动编码器
 * @author 彭清龙
 * @date 2019-12-02 下午 14:47
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf out) throws Exception {
        PacketCodeC.INSTANCE.encode(out, packet);
    }
}
