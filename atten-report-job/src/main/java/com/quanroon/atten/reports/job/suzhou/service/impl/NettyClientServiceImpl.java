package com.quanroon.atten.reports.job.suzhou.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.*;
import com.quanroon.atten.reports.job.suzhou.SuZhouClient;
import com.quanroon.atten.reports.job.suzhou.common.SyncFuture;
import com.quanroon.atten.reports.job.suzhou.service.NettyClientService;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
* netty客户端接口实现类
* 
* @Author: ysx
* @Date: 2020/8/4
*/
@Service
public class NettyClientServiceImpl implements NettyClientService {

    @Autowired
    private SuZhouClient suZhouClient;

    private static final Logger logger = LoggerFactory.getLogger(NettyClientServiceImpl.class);

    //缓存接口这里是LoadingCache，LoadingCache在缓存项不存在时可以自动加载缓存
    private static LoadingCache<String, SyncFuture> futureCache = CacheBuilder.newBuilder()
            //设置缓存容器的初始容量为10
            .initialCapacity(100)
            // maximumSize 设置缓存大小
            .maximumSize(10000)
            //设置并发级别为20，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(20)
            // expireAfterWrite设置写缓存后8秒钟过期
            .expireAfterWrite(8, TimeUnit.SECONDS)
            //设置缓存的移除通知
            .removalListener(new RemovalListener<Object, Object>() {
                @Override
                public void onRemoval(RemovalNotification<Object, Object> notification) {
                    logger.debug("LoadingCache: {} was removed, cause is {}",notification.getKey(), notification.getCause());
                }
            })
            //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
            .build(new CacheLoader<String, SyncFuture>() {
                @Override
                public SyncFuture load(String key) throws Exception {
                    // 当获取key的缓存不存在时，不需要自动添加
                    return null;
                }
            });

    /**
    * @Description: 向netty服务端发送消息
    * @Param: [byteBuf, method]
    * @return: com.alibaba.fastjson.JSONObject
    * @Author: ysx
    * @Date: 2020/8/4
    */
    @Override
    public JSONObject sendSyncMsg(ByteBuf byteBuf, String method) {

        SyncFuture<JSONObject> syncFuture = new SyncFuture<JSONObject>();
        // 放入缓存中
        futureCache.put(method, syncFuture);

        // 发送同步消息
        JSONObject result = suZhouClient.sendSyncMsg(byteBuf, syncFuture);

        return result;
    }

    /**
    * @Description: 确认从服务端收到的消息
    * @Param: [jsonObject]
    * @return: void
    * @Author: ysx
    * @Date: 2020/8/4
    */
    @Override
    public void ackSyncMsg(JSONObject jsonObject) {

        logger.info("ACK确认消息: {}",jsonObject.toJSONString());

        String method = jsonObject.getString("url");

        // 从缓存中获取数据
        SyncFuture<JSONObject> syncFuture = futureCache.getIfPresent(method);

        // 如果不为null, 则通知返回
        if(syncFuture != null) {
            syncFuture.setResponse(jsonObject);
            //主动释放
            futureCache.invalidate(method);
        }
    }

}
