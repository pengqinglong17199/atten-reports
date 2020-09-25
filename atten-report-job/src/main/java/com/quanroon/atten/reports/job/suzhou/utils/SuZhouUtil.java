package com.quanroon.atten.reports.job.suzhou.utils;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

/**
* 宿州工具类
*
* @Author: ysx
* @Date: 2020/8/4
*/
@Log4j
public class SuZhouUtil {

    /**
    * @Description: 将图片转成Base64编码并进行url编码
    * @Param: [imgFile]
    * @return: java.lang.String
    * @Author: ysx
    * @Date: 2020/8/4
    */
    public static String getImageBase64String(String imgFile) throws Exception{
        if(StringUtils.isEmpty(imgFile)) {
            return null;
        }

        if(StringUtils.indexOf(imgFile,"/zgz") != -1){
            imgFile = imgFile.replace("/zgz","");
        }

        InputStream inputStream = new FileInputStream(imgFile);

        byte[] data = new byte[inputStream.available()];
        inputStream.read(data);

        inputStream.close();

        //转码
        return Base64.getEncoder().encodeToString(data);
    }

    /**
    * @Description: 获取文件大小
    * @Param: [imgFile]
    * @return: int
    * @Author: ysx
    * @Date: 2020/8/4
    */
    public static int getFileSize(String imgFile) throws Exception{
        if(StringUtils.isEmpty(imgFile)) {
            return 0;
        }

        if(StringUtils.indexOf(imgFile,"/zgz") != -1){
            imgFile = imgFile.replace("/zgz","");
        }

        InputStream inputStream = new FileInputStream(imgFile);

        int available = inputStream.available();

        return available;
    }

    /**
    * @Description: 压缩图片
    * @Param: [imagePath, desFileSize]
    * @return: java.lang.String
    * @Author: ysx
    * @Date: 2020/8/5
    */
    public static String compressPicForScale(String imagePath, Integer desFileSize) throws Exception{
        BASE64Encoder encoder = new BASE64Encoder();

        InputStream inputStream = new FileInputStream(imagePath);

        byte[] data = new byte[inputStream.available()];
        inputStream.read(data);

        System.out.println("原始数据大小=======" + data.length);

        if (data.length < desFileSize * 1024) {
            return encoder.encode(data);
        }
        long srcSize = data.length;
        double accuracy = getAccuracy(srcSize / 1024);
        try {
            while (data.length > desFileSize * 1024) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
                Thumbnails.of(byteArrayInputStream)
                        .scale(accuracy)
                        .outputQuality(accuracy)
                        .toOutputStream(outputStream);
                data = outputStream.toByteArray();

            }
        } catch (Exception e) {
            log.error("【图片压缩】msg=图片压缩失败!", e);
        }
        return encoder.encode(data);
    }

    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2047) {
            accuracy = 0.6;
        } else if (size < 3275) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }

}
