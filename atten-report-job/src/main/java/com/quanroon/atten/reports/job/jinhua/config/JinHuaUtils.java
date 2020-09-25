/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.jinhua.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * @Auther: raojun
 * @Date: 2020/6/30 09:17
 * @Description:
 */
@Component
public class JinHuaUtils {

    private static String filePrefix;
    public static String getFilePrefix() {
        return filePrefix;
    }

    @Value("${server.fileBasePath:}")
    public void setFilePrefix(String filePrefix) {
        JinHuaUtils.filePrefix = filePrefix;
    }

    /**
     * 将图片转成Base64编码并进行url编码
     * @param imgFile
     * @return
     */
    public static String getImageBase64String(String imgFile) throws Exception{
        if(StringUtils.isEmpty(imgFile)) return null;
        if(StringUtils.indexOf(imgFile,"/zgz") != -1){
            imgFile = imgFile.replace("/zgz","");
        }
//        imgFile = filePrefix + imgFile;
        byte[] data = null;
        InputStream inputStream = new FileInputStream(imgFile);
        int available = inputStream.available();

        data = new byte[inputStream.available()];
        inputStream.read(data);

        inputStream.close();
        // 转码
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 获取文件大小
     * @param imgFile
     * @return
     */
    public static int getFileSize(String imgFile) throws Exception{
        if(StringUtils.isEmpty(imgFile)) return 0;
        if(StringUtils.indexOf(imgFile,"/zgz") != -1){
            imgFile = imgFile.replace("/zgz","");
        }
//        imgFile = filePrefix + imgFile;
        InputStream inputStream = null;
        inputStream = new FileInputStream(imgFile);
        int available = inputStream.available();

        return available;
    }
}
