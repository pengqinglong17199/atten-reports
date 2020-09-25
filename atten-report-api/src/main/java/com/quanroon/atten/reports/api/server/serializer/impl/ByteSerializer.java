package com.quanroon.atten.reports.api.server.serializer.impl;

import com.google.common.collect.Lists;
import com.quanroon.atten.commons.utils.DateUtils;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.api.server.annotation.Length;
import com.quanroon.atten.reports.api.server.entity.request.PacketRequest;
import com.quanroon.atten.reports.api.server.entity.response.PacketResponse;
import com.quanroon.atten.reports.api.server.serializer.Serializer;
import com.quanroon.atten.reports.api.server.serializer.SerializerAlgorithm;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 二进制序列化
 * @author 彭清龙
 * @date 2019-12-25 上午 11:23
 */
public class ByteSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.BYTE;
    }

    @Override
    public byte[] serialize(Object object) {
        PacketResponse packetResponse = (PacketResponse) object;

        byte flag = 0x00;
        if(packetResponse.getStatus()){
            flag = 0x01;
        }
        byte[] status = new byte[flag];
        String message = packetResponse.getMessage();
        if(StringUtils.isNotEmpty(message)){
            return this.byteMerger(message.getBytes(), status);
        }
        return status;
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) throws Exception {
        T t = clazz.newInstance();
        PacketRequest temp = (PacketRequest) t;
        int sumLength = temp.getSumLength();
        if(sumLength != bytes.length){
            throw new Exception();
        }

        List<Field> fields = Lists.newArrayList(clazz.getDeclaredFields());
        // 指针
        AtomicInteger integer = new AtomicInteger(0);
        fields.stream()
              .filter(field -> field.getAnnotation(Length.class) != null)
              .sorted(Comparator.comparing(field -> field.getAnnotation(Length.class).sort()))
              .forEachOrdered(field -> {
                  try {
                      field.setAccessible(true);
                      Length annotation = field.getAnnotation(Length.class);
                      int lenth = annotation.value();
                      byte[] data = this.readBytesFromTo(bytes, integer.get(), lenth);
                      integer.set(integer.get()+lenth);
                      Class<?> type = field.getType();
                      if (type.equals(String.class)) {

                          field.set(t, new String(data));
                      } else if (type.equals(Date.class)) {

                          String dateStr = new String(data);
                          Date date = DateUtils.parseDate(dateStr, "yyyyMMddHHmmss");
                          field.set(t, date);
                      } else if (type.equals(Integer.class)) {

                          int value = this.bytesToInt(data);
                          field.set(t, value);
                      }
                  }catch (Exception e){
                      e.printStackTrace();
                  }
              });
        if(bytes.length>0){
            byte[] bytes1 = this.readBytesFromTo(bytes, 0, bytes.length - 1);
            byte xor = this.getXor(bytes1);
            if(xor != bytes[bytes.length-1]){
                throw new Exception();
            }
        }
        return t;
    }

    /**
     *
     * xor校验
     * @author 彭清龙
     * @date 2019/6/25 10:30
     * @param datas
     * @return byte
     */
    private byte getXor(byte[] datas){

        byte temp=datas[0];
        for (int i = 1; i <datas.length; i++) {
            temp ^=datas[i];
        }

        return temp;
    }

    /**
     *
     * 将int数值转换为占四个字节的byte数组，(低位在前，高位在后)
     * @author 彭清龙
     * @date 2019/6/25 10:30
     * @param value
     * @return byte[]
     */
    private byte[] intToBytes( int value ) {
        byte[] src = new byte[4];
        src[3] =  (byte) ((value>>24) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }
    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用
     * @author 彭清龙
     * @date 2019/6/25 10:30
     * @param src byte数组
     * @return int数值
     */
    public int bytesToInt(byte[] src) {
        int value;
        value = (int) ((src[0] & 0xFF)
                | ((src[1] & 0xFF)<<8)
                | ((src[2] & 0xFF)<<16)
                | ((src[3] & 0xFF)<<24));
        return value;
    }
    /**
     *
     * 将short转换为两个字节的byte数组
     * @author 彭清龙
     * @date 2019/6/25 10:30
     * @param s
     * @return byte[]
     */
    private byte[] getCommendByte(short s) {
//        short s=(short)0xABCD;//原数ABCD二个字节
        byte a[]=new byte[2];//准备两个字节
        a[0]=(byte)(s&0xff); //获得低位字节
        a[1]=(byte)(s>>>8);//获得高位字节
        return a;
    }

    /**
     *
     * 合并数组
     * @author 彭清龙
     * @date 2019/6/25 10:29
     * @param bt1, bt2
     * @return byte[]
     */
    private byte[] byteMerger(byte[] bt1, byte[] bt2){
        byte[] bt3 = new byte[bt1.length+bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

    /**
     * 读取输入流中指定字节的长度
     * <p/>
     * 输入流
     *
     * @param length 指定长度
     * @return 指定长度的字节数组
     */
    public byte[] readBytesFromTo(byte[] buffer, int from, int length) {
        byte[] sub = new byte[length];
        int cur = 0;
        for (int i = from; i < length + from; i++) {
            sub[cur] = buffer[i];
            cur++;
        }
        return sub;
    }
}
