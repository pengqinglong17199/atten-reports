package com.quanroon.atten.reports.api.server.common;

import com.quanroon.atten.reports.api.server.entity.Packet;
import com.quanroon.atten.reports.api.server.entity.request.HeartbeatRequest;
import com.quanroon.atten.reports.api.server.entity.request.LoginRequest;
import com.quanroon.atten.reports.api.server.entity.request.SignlogRequest;
import com.quanroon.atten.reports.api.server.serializer.Serializer;
import com.quanroon.atten.reports.api.server.serializer.impl.ByteSerializer;
import com.quanroon.atten.reports.api.server.serializer.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * 封包工具类
 * @author 彭清龙
 * @date 2019-11-26 上午 8:59
 */
public class PacketCodeC {

    public static final PacketCodeC INSTANCE = new PacketCodeC();
    public static final byte MAGIC_NUMBER = 0x01;
    private static final Map<Short, Class<? extends Packet>> packetTypeMap ;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(SocketCommon.LOGIN, LoginRequest.class);
        packetTypeMap.put(SocketCommon.HEARTBEAT, HeartbeatRequest.class);
        packetTypeMap.put(SocketCommon.SIGNLOG, SignlogRequest.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
        serializer = new ByteSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {

        // 2. 序列化 Java 对象
        byte serializerType = packet.getSerializer();
        Serializer serializer = this.getSerializer(serializerType);
        byte[] bytes = serializer.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeByte(MAGIC_NUMBER);            // 开始标记
        byteBuf.writeByte(packet.getVersion());     // 版本号
        byteBuf.writeByte(serializerType);          // 序列化格式
        byteBuf.writeShort(packet.getCommand());    // 命令
        byteBuf.writeBytes(packet.getSessionId());  // 通信会话id
        byteBuf.writeInt(bytes.length);             // 包长度
        byteBuf.writeBytes(bytes);                  // 内容

        return byteBuf;
    }

    public String convertByteBufToString(ByteBuf buf) {
        String str;
        if(buf.hasArray()) { // 处理堆缓冲区
            str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
        } else { // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            str = new String(bytes, 0, buf.readableBytes());
        }
        return str;
    }

    public Packet decode(ByteBuf byteBuf) throws Exception {
        // 跳过 magic number
        byteBuf.skipBytes(1);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        short command = byteBuf.readShort();

        // 会话id
        byte[] sessionId = new byte[16];
        byteBuf.readBytes(sessionId);

        // 数据包长度
        int length = byteBuf.readInt();

        // 读取内容
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        // 获取对应class与序列化算法
        Class<? extends Packet> requestType = this.getRequestType(command);
        Serializer serializer = this.getSerializer(serializeAlgorithm);

        // 序列化对象
        if (requestType != null && serializer != null) {
            System.out.println("====================================>"+ new String(bytes));
            Packet deserialize = serializer.deserialize(requestType, bytes);
            deserialize.setSerializer(serializeAlgorithm);
            deserialize.setSessionId(sessionId);
            return deserialize;
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(short command) {

        return packetTypeMap.get(command);
    }
}
