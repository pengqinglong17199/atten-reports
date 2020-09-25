/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.henan.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * @Auther: Elvis
 * @Date: 2020-07-16 19:19
 * @Description: sha256工具
 */
public class Sha256X16Utils {
    private static Logger logger = LogManager.getLogger(Sha256X16Utils.class);
    /**
     * sha256计算后进行16进制转换
     *
     * @param data 待计算的数据
     * @param encoding 编码
     * @return 计算结果
     */
    public static byte[] sha256X16(String data, String encoding) {
        byte[] bytes = sha256(data, encoding);
        StringBuilder sha256StrBuff = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if (Integer.toHexString(0xFF & bytes[i]).length() == 1) {
                sha256StrBuff.append("0").append(
                        Integer.toHexString(0xFF & bytes[i]));
            } else {
                sha256StrBuff.append(Integer.toHexString(0xFF & bytes[i]));
            }
        }
        try {
            return sha256StrBuff.toString().getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }


    /**
     * sha256计算
     *
     * @param datas 待计算的数据
     * @param encoding  字符集编码
     * @return
     */
    private static byte[] sha256(String datas, String encoding) {
        try {
            return sha256(datas.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            logger.error("SHA256计算失败", e);
            return null;
        }
    }

    /**
     * sha256计算.
     * @param datas 待计算的数据
     * @return 计算结果
     */
    private static byte[] sha256(byte[] data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update(data);
            return md.digest();
        } catch (Exception e) {
            logger.error("SHA256计算失败", e);
            return null;
        }
    }
}
