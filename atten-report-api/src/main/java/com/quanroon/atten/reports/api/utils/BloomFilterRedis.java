package com.quanroon.atten.reports.api.utils;

import com.google.common.collect.Lists;
import com.google.common.hash.Funnels;
import com.quanroon.atten.reports.entity.UpPlatformAuth;
import com.quanroon.atten.reports.entity.example.UpPlatformAuthExample;
import com.quanroon.atten.reports.service.UpPlatformAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content redis操作BloomFilter,适用分布式
 * @date 2020/8/21 10:47
 */
@Component
public class BloomFilterRedis<T> {
    /**bloomfiter 鉴权服务key*/
    public final String BLOOM_SECRET_KEY = "bloom_filter:auth_secret_key";

    private static final BloomFilterHelper  bloomFilterHelper = new BloomFilterHelper<>(
            Funnels.stringFunnel(Charset.defaultCharset()), BloomFilterHelper.expectedInsertions);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UpPlatformAuthService upPlatformAuthService;

    /**
     * 删除缓存的KEY
     *
     * @param key KEY
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
    * @Description: 根据给定的布隆过滤器添加值，在添加一个元素的时候使用，批量添加的性能差
    * @Author: quanroon.yaosq
    * @Date: 2020/8/21 11:17
    * @Param: [key, value]
    * @Return: void
    */
    public <T> void add(String key, T value) {
        int[] offset = bloomFilterHelper.hashOffset(value);
        for (int i : offset) {
            redisTemplate.opsForValue().setBit(key, i, true);
        }
    }
    /**
    * @Description: 检查key中是否存在,并重新设置
    * @Author: quanroon.yaosq
    * @Date: 2020/8/21 15:24
    * @Param: [key]
    * @Return: void
    */
    public void getBloomCountToSet(String key){
        if(!redisTemplate.hasKey(key)){
            List<UpPlatformAuth> upPlatformAuths = upPlatformAuthService.selectByExample(new UpPlatformAuthExample());
            List<String> valueList = Lists.newArrayList();
            upPlatformAuths.forEach(upPlatformAuth -> {
                valueList.add(upPlatformAuth.getAppId() + upPlatformAuth.getAppKey());
            });
            if(valueList.size() > 0){
                addList(key, valueList);
                redisTemplate.expire(key, 7, TimeUnit.DAYS);
            }
        }
    }
    /**
    * @Description: 根据给定的布隆过滤器添加值，在添加一批元素的时候使用，批量添加的性能好，使用pipeline方式(如果是集群下，请使用优化后RedisPipeline的操作)
    * @Author: quanroon.yaosq
    * @Date: 2020/8/21 11:18
    * @Param: [key, valueList] valueList:待新增的数据集合
    * @Return: void
    */
    public <T> void addList(String key, List<T> valueList) {
        redisTemplate.executePipelined(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (T value : valueList) {
                    int[] offset = bloomFilterHelper.hashOffset(value);
                    for (int i : offset) {
                        connection.setBit(key.getBytes(), i, true);
                    }
                }
                return null;
            }
        });
    }

    /**
    * @Description: 根据给定的布隆过滤器判断值是否存在
    * @Author: quanroon.yaosq
    * @Date: 2020/8/21 11:16
    * @Param: [bloomFilterHelper, key, value] value:泛型，可以传入任何类型的value
    * @Return: boolean 是否存在
    */
    public <T> boolean contains(String key, T value) {
        getBloomCountToSet(key);
        int[] offset = bloomFilterHelper.hashOffset(value);
        for (int i : offset) {
            if (!redisTemplate.opsForValue().getBit(key, i)) {
                return false;
            }
        }
        return true;
    }
}
