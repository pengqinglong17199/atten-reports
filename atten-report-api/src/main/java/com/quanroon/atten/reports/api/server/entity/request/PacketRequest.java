package com.quanroon.atten.reports.api.server.entity.request;

import com.google.common.collect.Lists;
import com.quanroon.atten.reports.api.server.entity.Packet;
import com.quanroon.atten.reports.api.server.annotation.Length;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 请求父类
 * @author 彭清龙
 * @date 2019-12-23 下午 20:40
 */
@Data
public abstract class PacketRequest extends Packet {

    Integer id;

    public int getSumLength(){

        List<Field> lengths = Lists.newArrayList(this.getClass().getDeclaredFields());
        AtomicInteger integer = new AtomicInteger();
        lengths.stream()
               .filter(field -> field.getAnnotation(Length.class) != null)
               .forEach(field -> integer.set(integer.get()+field.getAnnotation(Length.class).value()));
        return integer.get();
    }
}
