package com.quanroon.atten.reports.api.server.entity;

import lombok.Data;

/**
 * @author 彭清龙
 * @date 2019-12-23 下午 20:06
 */
@Data
public abstract class Packet {

    /**开始标记*/
    protected Byte header = 0x01;
    /**版本*/
    protected Byte version = 0x01;
    /**序列化算法*/
    protected Byte serializer;
    /*** 命令*/
    public abstract short getCommand();
    /**会话标识*/
    protected byte[] sessionId;
    /**长度len*/
    protected Integer length;
    /**状态 0x00成功 0x01失败*/
    protected Boolean status = true;

}
