package com.quanroon.atten.reports.api.server.serializer;


import com.quanroon.atten.reports.api.server.serializer.impl.ByteSerializer;

public interface Serializer {

    Serializer DEFAULT = new ByteSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();
    
    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes) throws Exception;
}