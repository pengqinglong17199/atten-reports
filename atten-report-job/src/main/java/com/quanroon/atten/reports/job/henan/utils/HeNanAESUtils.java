/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.henan.utils;

import cn.hutool.core.codec.Base64Encoder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Security;

/**
 * @Auther: Elvis
 * @Date: 2020-07-21 19:05
 * @Description:
 */
public class HeNanAESUtils {

    public static String encrpty(String key,String data){
        try{
            IvParameterSpec ivParameterSpec = new IvParameterSpec(key.substring(0,16).getBytes());
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.substring(0,16).getBytes(),"AES");
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivParameterSpec);
            return Base64Encoder.encode(cipher.doFinal(data.getBytes()),"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static IvParameterSpec getIV(String secretKey) {
        StringBuilder sb = new StringBuilder(secretKey.length());
        sb.append(secretKey);
        if (sb.length() > 16) {
            sb.setLength(16);
        } else {
            while (sb.length() < 16) {
                sb.append("0");
            }
        }
        byte[] data = null;
        try {
            data = sb.toString().getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assert data != null;
        return new IvParameterSpec(data);
    }
}
