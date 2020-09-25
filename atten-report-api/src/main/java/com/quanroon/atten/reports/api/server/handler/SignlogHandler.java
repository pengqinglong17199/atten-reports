package com.quanroon.atten.reports.api.server.handler;

import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.api.server.common.SessionUtil;
import com.quanroon.atten.reports.api.server.entity.Session;
import com.quanroon.atten.reports.api.server.entity.request.SignlogRequest;
import com.quanroon.atten.reports.api.server.entity.response.SignlogResponse;
import com.quanroon.atten.reports.entity.UpDeviceInfo;
import com.quanroon.atten.reports.entity.UpWorkerInfo;
import com.quanroon.atten.reports.entity.UpWorkerSignlogInfo;
import com.quanroon.atten.reports.entity.dto.UpWorkerSignlogInfoDTO;
import com.quanroon.atten.reports.service.UpDeviceInfoService;
import com.quanroon.atten.reports.service.UpWorkerInfoService;
import com.quanroon.atten.reports.service.UpWorkerSignlogInfoService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * @Description: 考勤
 * @Author: ysx
 * @Date: 2020/7/1
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class SignlogHandler extends SimpleChannelInboundHandler<SignlogRequest> {

    @Autowired
    private UpWorkerSignlogInfoService upWorkerSignlogInfoServiceImpl;
    @Autowired
    private UpWorkerInfoService upWorkerInfoServiceImpl;
    @Autowired
    private UpDeviceInfoService upDeviceInfoServiceImpl;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SignlogRequest signlogRequest){
        // 封装响应
        SignlogResponse signlogResponse = new SignlogResponse(signlogRequest);
        try {

            // 获取session 刷新心跳
            Session session = SessionUtil.getSession(ctx.channel());
            session.setHeartbeatDate(new Date());

            // 校验数据
            String message = this.valid(signlogRequest, session);
            if (StringUtils.isNotEmpty(message)) {
                // 数据错误 响应错误
                signlogResponse.setStatus(false);
                signlogResponse.setId(signlogRequest.getId());
                signlogResponse.setMessage(message);
                ctx.channel().writeAndFlush(signlogResponse);
                return;
            }

            // 保存考勤
            UpWorkerSignlogInfoDTO dto = signlogRequest.getSignlog();
            UpDeviceInfo upDeviceInfo = upDeviceInfoServiceImpl.findDeviceInfoById(dto.getDeviceId());
            if(Objects.isNull(upDeviceInfo)){
                signlogResponse.setStatus(false);
                signlogResponse.setId(signlogRequest.getId());
                signlogResponse.setMessage("考勤机未上报");
                ctx.channel().writeAndFlush(signlogResponse);
                return;
            }

            //没有考勤图片不给上报
            if(StringUtils.isEmpty(signlogRequest.getPhoto())){
                signlogResponse.setStatus(false);
                signlogResponse.setId(signlogRequest.getId());
                signlogResponse.setMessage("考勤图片为空");
                ctx.channel().writeAndFlush(signlogResponse);
                return;
            }

            //封装数据
            UpWorkerSignlogInfo signlogInfo = new UpWorkerSignlogInfo();
            this.packageSignlog(signlogInfo,dto);
            signlogInfo.setDeviceSn(upDeviceInfo.getDeviceSn());
            signlogInfo.setProjId(upDeviceInfo.getProjId());

            //生成消息
            String requestCode = upWorkerSignlogInfoServiceImpl.save(signlogInfo,dto);

            //响应成功
            signlogResponse.setStatus(true);
            signlogResponse.setRequestCode(requestCode);
            signlogResponse.setProjId(session.getProjId());
            signlogResponse.setId(signlogRequest.getId());
            signlogResponse.setIoTime(signlogRequest.getIoTime());
            ctx.channel().writeAndFlush(signlogResponse);
        } catch (Exception e) {
            e.printStackTrace();
            signlogResponse.setId(signlogRequest.getId());
            signlogResponse.setStatus(false);
            signlogResponse.setMessage("服务器繁忙");
            ctx.channel().writeAndFlush(signlogResponse);
        }
    }

    /**
     * 校验数据
     *
     * @param signlogRequest, session
     * @return java.lang.String
     * @author ysx
     * @date 2020/7/1
     */
    private String valid(SignlogRequest signlogRequest, Session session){

        // 校验劳工身份是否存在
        UpWorkerInfo worker = session.getWorker(signlogRequest.getWorkerId());
        if (worker == null) {
            worker = upWorkerInfoServiceImpl.findWorkerInfoByWorkerId(signlogRequest.getWorkerId());
            if (worker == null) {
                return "劳工不存在";
            }
            session.setWorker(signlogRequest.getWorkerId(), worker);
        }
        return null;
    }

    /**
    * @Description: 封装要保存的考勤数据
    * @Author: ysx
    * @Date: 2020/7/10
    */
    public UpWorkerSignlogInfo packageSignlog(UpWorkerSignlogInfo signlogInfo,UpWorkerSignlogInfoDTO dto){
        signlogInfo.setLat(dto.getLat().doubleValue());
        signlogInfo.setLgt(dto.getLgt().doubleValue());
        signlogInfo.setDirection(dto.getIoType().toString());
        signlogInfo.setType(dto.getIoMode().toString());
        signlogInfo.setSimilar(dto.getSimilar().doubleValue());
        signlogInfo.setTime(dto.getIoTime());
        signlogInfo.setWorkerId(dto.getWorkerId());
        return signlogInfo;
    }


}
