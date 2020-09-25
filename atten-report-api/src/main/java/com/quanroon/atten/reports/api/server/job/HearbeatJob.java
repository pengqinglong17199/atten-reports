package com.quanroon.atten.reports.api.server.job;

import com.quanroon.atten.commons.utils.DateUtils;
import com.quanroon.atten.reports.api.server.common.SessionUtil;
import com.quanroon.atten.reports.api.server.common.SocketConfig;
import com.quanroon.atten.reports.api.server.entity.Session;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

/**
 * 心跳监测定时器
 * @author 彭清龙
 * @date 2020-01-10 下午 16:56
 */
@Component
@Lazy(false)
@Slf4j
public class HearbeatJob {

    @Scheduled(cron = "0 0/1 * * * ?")
    public void hearbeat(){
        Set<String> keySet = SessionUtil.keySet();
        keySet.forEach(key -> {

            Channel channel = SessionUtil.getChannel(key);
            Session session = SessionUtil.getSession(channel);

            Date heartbeatDate = session.getHeartbeatDate();
            Integer ioTimeStr = Integer.valueOf(DateUtils.formatDate(heartbeatDate, "MMddHHmm"));
            Integer newTimeStr = Integer.valueOf(DateUtils.formatDate(new Date(), "MMddHHmm"));

            // 超时
            if(ioTimeStr - newTimeStr > SocketConfig.HEARTBEAT_OUT_TIME){
                SessionUtil.unBindSession(channel);
                channel.close();
            }
        });
    }
}
