package com.quanroon.atten.reports.api.server.common;

import com.quanroon.atten.reports.api.server.entity.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * session工具
 * @author 彭清龙
 * @date 2019-12-23 下午 20:08
 */
public class SessionUtil {
    // sessionId -> channel 的映射
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();


    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getSessionId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getSessionId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }
    
    public static boolean hasLogin(Channel channel) {

        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }

    public static Set<String> keySet(){
        return userIdChannelMap.keySet();
    }
}