/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.jinhua.config;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Auther: raojun
 * @Date: 2020/6/29 14:18
 * @Description:
 */
public class Sha256 {
    /**
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256(String str) {
        String encodestr = DigestUtils.sha256Hex(str);
        return encodestr;
    }

    public static void main(String[] args) {
        String holdEncodeStr = "appid=0cee85d6e82d430e92adb778b7002765&data={\"teamname\":\"水泥板组\"}&format=json&method=team.add&nonce=401772&timestamp=20200701143719&version=1.0&appsecret=581c49df6ac44fcb9b4f90c42271046f";
        String sha256 = getSHA256(holdEncodeStr.toLowerCase());
        System.out.println(sha256);
    }
}
