/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.jinhua.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: raojun
 * @Date: 2020/6/29 11:55
 * @Description:
 */
public class ReportHttpUtils {
    public static final Logger logger = LoggerFactory.getLogger(ReportHttpUtils.class);

    /**
     * @param url
     * @param headMap
     * @param parmsMap
     * @param jsonParmStr
     * @return
     */
    public static JSONObject httpPost(String url, Map<String, String> headMap, Map<String, String> parmsMap, String jsonParmStr,
                                      Map<String, String> urlParamsMap, String contentType) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String content = null;
        try {
            URLEncoder.encode(url);
            URIBuilder uriBuilder = new URIBuilder(url);
            if (null != urlParamsMap && urlParamsMap.size() > 0) {
                List<NameValuePair> list = new LinkedList<>();
                for (Map.Entry<String, String> en : urlParamsMap.entrySet()) {
                    String key = en.getKey();
                    String value = en.getValue();

                    BasicNameValuePair param = new BasicNameValuePair(key, value);
                    list.add(param);
                }
                uriBuilder.setParameters(list);
            }
            logger.info("httpPost URIBuilder [" + uriBuilder.build() + "]");
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            httpPost.addHeader("Content-Type", contentType);

            //设置请求头参数
            if (null != headMap && headMap.size() > 0) {
                for (Map.Entry<String, String> en : headMap.entrySet()) {
                    String key = en.getKey();
                    String value = en.getValue();
                    httpPost.setHeader(key, value);
                }
            }

            //setConnectTimeout：设置连接超时时间，单位毫秒。setConnectionRequestTimeout：
            // 设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
            // setSocketTimeout：请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(15000).build();
            httpPost.setConfig(defaultRequestConfig);

            //设置键值对请求参数
            List<NameValuePair> list = new ArrayList<>();
            if (null != parmsMap && parmsMap.size() > 0) {
                for (Map.Entry<String, String> en : parmsMap.entrySet()) {
                    String key = en.getKey();
                    String value = en.getValue();
                    BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
                    list.add(basicNameValuePair);
                }
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list, "UTF-8");
                httpPost.setEntity(formEntity);
            }

            //设置JSON字符串请求参数
            if (StringUtils.isNotBlank(jsonParmStr)) {
                StringEntity stringEntity = new StringEntity(jsonParmStr, "UTF-8");
                httpPost.setEntity(stringEntity);
            }

            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                content = EntityUtils.toString(response.getEntity());
            }
            logger.info("ReportHttpUtils " + content);
        } catch (Exception e) {
            logger.error("ReportHttpUtils httpPost URL [" + url + "] error, ", e);
        }

        //返回JSONObject
        if (null == content) {
            return new JSONObject();
        } else {
            return JSON.parseObject(content);
        }
    }

    /**
     * @param url
     * @param headMap
     * @param parmsMap
     * @return
     */
    public static JSONObject httpGet(String url, Map<String, String> headMap, Map<String, String> parmsMap) {
        logger.info("httpPost URL [" + url + "] start ");
        logger.info("httpPost headMap :" + JSON.toJSONString(headMap));
        logger.info("httpPost parmsMap :" + JSON.toJSONString(parmsMap));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String content = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (null != parmsMap && parmsMap.size() > 0) {
                List<NameValuePair> list = new LinkedList<>();
                for (Map.Entry<String, String> en : parmsMap.entrySet()) {
                    String key = en.getKey();
                    String value = en.getValue();

                    BasicNameValuePair param = new BasicNameValuePair(key, value);
                    list.add(param);
                }
                uriBuilder.setParameters(list);
            }

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            //setConnectTimeout：设置连接超时时间，单位毫秒。setConnectionRequestTimeout：
            // 设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
            // setSocketTimeout：请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(15000).build();
            httpGet.setConfig(defaultRequestConfig);

            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("ReportHttpUtils " + content);
            }
        } catch (Exception e) {
            logger.error("ReportHttpUtils httpGet URL [" + url + "] error, ", e);
            e.printStackTrace();
        }

        //返回JSONObject
        if (null == content) {
            return new JSONObject();
        } else {
            return JSON.parseObject(content);
        }
    }
}
