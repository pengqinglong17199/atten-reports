/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.henan.utils;

import cn.hutool.core.codec.Base64Encoder;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: Elvis
 * @Date: 2020-07-21 17:12
 * @Description: 图片处理工具类
 */
@Slf4j
public class ImageUtils {

    public static String encodePicture(String path){
        try {
            File file = new File(path);
            String base64 = null;
            List<File> list = new ArrayList<>();
            while (file.length() / 1024 > 300) {
                log.info("图片压缩前大小--{}kb", file.length() / 1024);
                File tempFile = File.createTempFile(UUID.randomUUID().toString(), ".jpg");

                Thumbnails.of(file).scale(0.5).outputFormat("jpg").toFile(tempFile);
                file = tempFile;
                list.add(tempFile);
                log.info("图片压缩后大小--{}kb", file.length() / 1024);
            }
            byte[] bytes = Files.readAllBytes(file.toPath());
            base64 = Base64.getEncoder().encodeToString(bytes);

            for (File temp : list) {
                temp.delete();
            }
            return base64;
        }catch (FileNotFoundException ffe){
            ffe.printStackTrace();
        }catch (IOException ie){
            ie.printStackTrace();
        }
        return null;
    }
}
