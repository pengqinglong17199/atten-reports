package com.quanroon.atten.reports.api.server.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.quanroon.atten.reports.api.server.serializer.Serializer;
import com.quanroon.atten.reports.api.server.serializer.SerializerAlgorithm;

public class JSONSerializer implements Serializer {
   
    @Override
    public byte getSerializerAlgorithm() {
        
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        
        return JSON.parseObject(bytes, clazz);
    }
}