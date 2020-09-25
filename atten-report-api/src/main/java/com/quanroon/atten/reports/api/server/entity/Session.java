package com.quanroon.atten.reports.api.server.entity;

import com.google.common.collect.Maps;
import com.quanroon.atten.reports.entity.UpWorkerInfo;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * 连接session信息
 * @author 彭清龙
 * @date 2019-12-24 上午 11:18
 */
@Data
public class Session {

    private String authKey;                         // 项目key
    private String sessionId;                       // 会话标识
    private Integer projId;                         // 项目id
    private Date heartbeatDate;                     // 心跳时间
    private Map<Integer, UpWorkerInfo> workers;     // 劳工缓存

    public Session(String authKey, String sessionId, Integer projId){
        this.authKey = authKey;
        this.sessionId = sessionId;
        this.projId = projId;
        this.heartbeatDate = new Date();
        this.workers = Maps.newHashMap();
    }

    public UpWorkerInfo getWorker(Integer workerId) {
       return workers.get(workerId);
    }

    public UpWorkerInfo setWorker(Integer workerId, UpWorkerInfo workerInfo) {
        return workers.put(workerId, workerInfo);
    }
}
