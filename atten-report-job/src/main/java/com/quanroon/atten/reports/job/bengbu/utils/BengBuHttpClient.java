package com.quanroon.atten.reports.job.bengbu.utils;

import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.reports.job.bengbu.config.BengBuCongig;
import jdk.nashorn.internal.objects.Global;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;




/**
 * @author 罗森林
 * @Auther:
 * @Date: 2020-07-20 11:51
 * @Description: 接口工具类
 */
@Slf4j
public class BengBuHttpClient {


    /**
     * Post请求接口
     * @auther: 罗森林
     * @param: url 接口路径  prarm 参数  reType 请求类型
     * @return:
     * @date: 2020-08-27 14:12
     */
    public static String PostRequest(String url,String param,String reType){
        HttpResponse response = null;
        HttpEntity entity = null;
        String result = "";
        try{
            //编码
            CloseableHttpClient httpClient = HttpClients.createDefault();
            if(reType.equals(BengBuCongig.REQUEST_QUERY)){
                url = url + "?" + param;
            }
            HttpPost httpPost = new HttpPost(url);
            // 设置请求头
            httpPost.setHeader("Content-Type", "application/json");
            //设置参数
            StringEntity se = new StringEntity(param,"utf-8");
            se.setContentType("text/json");
            httpPost.setEntity(se);
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(15000).build();
            httpPost.setConfig(defaultRequestConfig);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException();
            } else {
                entity = response.getEntity();
                if (null != entity) {
                    byte[] bytes = EntityUtils.toByteArray(entity);
                    result = new String(bytes, "UTF-8");
                    log.debug("返回日志: "+ url + result);
                } else {
                    log.error("httpPost URL [" + url + "],httpEntity is null.");
                }
                return result;
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
