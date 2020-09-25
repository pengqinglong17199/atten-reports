package com.quanroon.atten.reports.job.suzhou.service;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;

/**
* netty客户端接口
*
* @Author: ysx
* @Date: 2020/8/4
*/
public interface NettyClientService {

    /**
    * @Description: 发送信息给服务端
    * @Param: [byteBuf, method]
    * @return: com.alibaba.fastjson.JSONObject
    * @Author: ysx
    * @Date: 2020/8/4
    */
    JSONObject sendSyncMsg(ByteBuf byteBuf,String method);

    /**
    * @Description: 确认从服务端收到返回的信息
    * @Param: [jsonObject]
    * @return: void
    * @Author: ysx
    * @Date: 2020/8/4
    */
    void ackSyncMsg(JSONObject jsonObject);


}
