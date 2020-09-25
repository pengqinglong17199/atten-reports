package com.quanroon.atten.reports.job.henan.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.reports.entity.UpParams;
import com.quanroon.atten.reports.job.henan.config.HeNanConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: Elvis
 * @date: 2020-07-22 11:22
 * @Description: 河南上报工具类
 */
@Slf4j
public class HeNanHttpUtils {

    /**
     * post请求
     * @param method
     * @param o
     * @return
     */
    public static JSONObject httpPost(String method, Object o,UpParams upParams){
        List listData = new ArrayList();
        listData.add(o);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHH");
        String timestamp = sdf.format(new Date());
        String nonce = getNonce(timestamp);
        String oJson = JSON.toJSONString(listData);
        System.out.println("=================================>"+oJson);
        String content = null;
        try{
            StringBuffer sb=new StringBuffer("appid="+ upParams.getKey());
            sb.append("&data="+oJson);
            sb.append("&format=json");
            sb.append("&iscompany=1");
            sb.append("&method="+method);
            sb.append("&nonce="+nonce);
            sb.append("&timestamp="+timestamp);
            sb.append("&version="+HeNanConfig.VERSION);
            String sign = createSign(sb.toString(),upParams.getSecret());
            StringBuffer sb2=new StringBuffer("appid="+upParams.getKey());
            sb2.append("&data="+ oJson.replaceAll("\\+", "%2B"));
            sb2.append("&format=json");
            sb2.append("&iscompany=1");
            sb2.append("&method="+method);
            sb2.append("&nonce="+nonce);
            sb2.append("&timestamp="+timestamp);
            sb2.append("&version="+HeNanConfig.VERSION);
            sb2.append("&sign="+sign);
            String requestBody=sb2.toString();
            log.error("===========================》河南上报接口请求参数:"+requestBody);
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(HeNanConfig.UPLOAD_URL);
            StringEntity postingString = new StringEntity(requestBody,"UTF-8");
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/x-www-form-urlencoded");
            HttpResponse response = httpClient.execute(post);
            content = EntityUtils.toString(response.getEntity());
        }catch (IOException ie){
            ie.printStackTrace();
        }
        log.error("===========================》河南上报接口返回结果:"+content);
        return JSON.parseObject(content);
    }

    /**
     * 查询结果
     * @param requestCode
     * @return
     */
    public static JSONObject findResult(String requestCode,UpParams upParams){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("REQUESTCODE",requestCode);
        try{
            Thread.sleep(3000l);
            return httpPost("AsyncHandleResult",resultMap,upParams);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 查询结果
     * @param requestCode
     * @return
     */
    public static JSONObject findResultSignlog(String requestCode,UpParams upParams,Long waitTimes){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("REQUESTCODE",requestCode);
        try{
            Thread.sleep(waitTimes);
            return httpPost("AsyncHandleResult",resultMap,upParams);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String createSign(String s, String appsecret) {
        String parm=s+"&appsecret=" + appsecret;
        return  new String(Sha256X16Utils.sha256X16(parm.toLowerCase(), "utf-8"));
    }

    private static String getNonce(String timestamp) {
        Random random = new Random();
        int i = random.nextInt(100);
        return timestamp+i;
    }


    private static String getTimestamp() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmm");
        return sdf.format(new Date());
    }

}