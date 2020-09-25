package com.quanroon.atten.reports.job.jinhua.config;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;

public class AESUtils {

    private static final Logger logger = LoggerFactory.getLogger(AESUtils.class);

    /**
     * IV 16位
     */
    private static final Integer iv_size = 16;

    /**
     * 编码
     */
    private static final String charset = "UTF-8";

    /**
     * "算法/模式/补码方式"
     */
    private static final String cipher_mode = "AES/CBC/PKCS7Padding";


    /**
     *加密
     * @param content
     * @param key
     * @return
     */
    public static String aesEncrypt(String content, String key){
        String result = "";
        try {
            byte[] contentBytes = content.getBytes(charset);
            byte[] keyBytes = key.getBytes(charset);
            byte[] encryptedBytes = aesEncryptBytes(contentBytes, keyBytes, getIV(key));
            Base64.Encoder encoder = Base64.getEncoder();
            result = encoder.encodeToString(encryptedBytes);
        } catch (Exception e) {
            logger.error("AESUtils aesEncrypt error " + e.getMessage());
        }
        return result;
    }

    /**
     * 解密
     * @param content
     * @param key
     * @return
     */
    public static String aesDecrypt(String content, String key) {
        String result = "";
        try{
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encryptedBytes = decoder.decode(content);
            byte[] keyBytes = key.getBytes(charset);
            byte[] decryptedBytes = aesDecryptBytes(encryptedBytes, keyBytes, getIV(key));
            result = new String(decryptedBytes, charset);
        }catch (Exception e) {
            logger.error("AESUtils aesDecrypt error " + e.getMessage());
        }
        return result;
    }

    private static byte[] aesEncryptBytes(byte[] contentBytes, byte[] keyBytes, IvParameterSpec ivParameterSpec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE, ivParameterSpec);
    }

    private static byte[] aesDecryptBytes(byte[] contentBytes, byte[] keyBytes, IvParameterSpec ivParameterSpec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return cipherOperation(contentBytes, keyBytes, Cipher.DECRYPT_MODE, ivParameterSpec);
    }

    private static byte[] cipherOperation(byte[] contentBytes, byte[] keyBytes, int mode, IvParameterSpec ivParameterSpec) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Security.addProvider(new BouncyCastleProvider());
        Key key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance(cipher_mode);
        cipher.init(mode, key, ivParameterSpec);
        return cipher.doFinal(contentBytes);
    }

    private static IvParameterSpec getIV(String secretKey) {
        StringBuilder sb = new StringBuilder(secretKey.length());
        sb.append(secretKey);
        if (sb.length() > iv_size) {
            sb.setLength(iv_size);
        } else {
            while (sb.length() < iv_size) {
                sb.append("0");
            }
        }
        byte[] data = null;
        try {
            data = sb.toString().getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assert data != null;
        return new IvParameterSpec(data);
    }
}
