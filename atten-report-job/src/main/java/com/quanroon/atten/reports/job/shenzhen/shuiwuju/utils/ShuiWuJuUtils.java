package com.quanroon.atten.reports.job.shenzhen.shuiwuju.utils;

import cn.hutool.core.util.StrUtil;
import com.quanroon.atten.commons.utils.http.HttpConnectionManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 深圳水务局工具类
 *
 * @Author: ysx
 * @Date: 2020/8/12
 */
@Slf4j
public class ShuiWuJuUtils {

    /**
    * @Description: 拼接加密参数
    * @Param: [api_secret, body, api_Key, api_Version, client_Serial, timeStamp]
    * @return: java.lang.String
    * @Author: ysx
    * @Date: 2020/8/12
    */
    public static String getSignature(String api_secret, String body, String api_Key, String api_Version,
                                      String client_Serial,String timeStamp) throws NoSuchAlgorithmException {
        StringBuilder str4MD5 = new StringBuilder();
        str4MD5.append(api_secret)
                .append("api_key")
                .append(api_Key)
                .append("api_version")
                .append(api_Version)
                .append("body")
                .append(StrUtil.isEmpty(body) ? "{}" : body)
                .append("client_serial")
                .append(client_Serial)
                .append("timestamp")
                .append(timeStamp)
                .append(api_secret);
        String str = GetMD5Code(str4MD5.toString());
        return str;
    }

    /**
     * @Description: 获取32位大写MD5加密，strObj为原文
     * @Param: [strObj]
     * @return: java.lang.String
     * @Author: ysx
     * @Date: 2020/8/12
     */
    private static String GetMD5Code(String strObj) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        Charset charset = Charset.forName("UTF-8");
        //md.digest()该函数返回值为存放哈希值结果的byte数组
        String resultString = byteToString(md.digest(strObj.getBytes(charset)));
        return resultString;
    }

    /**
     * @Description: 转换字节数组为16进制字串
     * @Param: [bytes]
     * @return: java.lang.String
     * @Author: ysx
     * @Date: 2020/8/12
     */
    private static String byteToString(byte[] bytes) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sBuffer.append(byteToArrayString(bytes[i]));
        }
        return sBuffer.toString();
    }

    /**
     * @Description: 返回形式为数字跟字符串(大写)
     * @Param: [bByte]
     * @return: java.lang.String
     * @Author: ysx
     * @Date: 2020/8/12
     */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    /** 全局数组*/
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
            "E", "F"};


    /**
    * @Description: 图片转base64
    * @Param: [imagePath]
    * @return: java.lang.String
    * @Author: ysx
    * @Date: 2020/8/17
    */
    public static String getBase64(String imagePath){
        try {
            BASE64Encoder encoder = new BASE64Encoder();

            InputStream inputStream = new FileInputStream(imagePath);

            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);

            return encoder.encode(data);
        }catch (Exception e){
            log.info("图片转base64失败");
        }
        return null;
    }

    public static String post(String url,Map paramvalue){
        log.info(String.format("httpPost URL [%s] start ", url));
        log.info(String.format("httpPost body :%s", paramvalue));

        HttpClient httpclient = null;
        HttpPost httpPost = null;
        HttpResponse response = null;
        HttpEntity entity = null;
        String result = "";

        try {
            url = getDictUrl(url,paramvalue);

            httpclient = HttpConnectionManager.getHttpClient();
            httpPost = new HttpPost(url);

            /*
            setConnectTimeout：设置连接超时时间，单位毫秒。setConnectionRequestTimeout：
            设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
            setSocketTimeout：请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            */
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(15000).build();
            httpPost.setConfig(defaultRequestConfig);
            response = httpclient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                log.error("Method failed: " + response.getStatusLine());
                return "";
            } else {
                entity = response.getEntity();
                if (null != entity) {
                    byte[] bytes = EntityUtils.toByteArray(entity);
                    result = new String(bytes, "UTF-8");
                    log.debug("httpPost response: " + result);
                } else {
                    log.error("httpPost URL [" + url + "],httpEntity is null.");
                }
                return result;
            }
        } catch (Exception e) {
            log.error("httpPost URL [" + url + "] error, ", e);
            return "";
        }
    }

    private static String getDictUrl(String url, Map map){
        StringBuilder sb = new StringBuilder(url);
        try {
            sb.append("?api_key=").append(map.get("api_key").toString())
                    .append("&api_version=").append(map.get("api_version").toString())
                    .append("&client_serial=").append(map.get("client_serial").toString())
                    .append("&timestamp=").append(URLEncoder.encode(map.get("timestamp").toString(), "UTF-8"))
                    .append("&signature=").append(map.get("signature").toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
