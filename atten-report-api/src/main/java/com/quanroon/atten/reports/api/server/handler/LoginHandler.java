package com.quanroon.atten.reports.api.server.handler;


import com.quanroon.atten.reports.api.server.common.SessionUtil;
import com.quanroon.atten.reports.api.server.entity.Session;
import com.quanroon.atten.reports.api.server.entity.request.LoginRequest;
import com.quanroon.atten.reports.api.server.entity.response.LoginResponse;
import com.quanroon.atten.reports.entity.UpProjectAuth;
import com.quanroon.atten.reports.entity.UpProjectInfo;
import com.quanroon.atten.reports.entity.example.UpProjectAuthExample;
import com.quanroon.atten.reports.service.UpProjectAuthService;
import com.quanroon.atten.reports.service.UpProjectInfoService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
* @Description: 登录
* @Author: ysx
* @Date: 2020/7/1
*/
@Component
@ChannelHandler.Sharable
@Slf4j
public class LoginHandler extends SimpleChannelInboundHandler<LoginRequest> {

    @Autowired
    private UpProjectInfoService upProjectInfoServiceImpl;
    @Autowired
    private UpProjectAuthService upProjectAuthServiceImpl;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequest loginRequest) throws Exception {
        // 封装响应
        LoginResponse loginResponse = new LoginResponse(loginRequest);
        // 登录校验
        Integer valid = this.valid(loginRequest);
        if (valid != null) {
            // 登录成功 保存session信息
            String sessionId = new String(loginRequest.getSessionId(), "utf-8");
            SessionUtil.bindSession(new Session(loginRequest.getAuthKey().toString(), sessionId, valid), ctx.channel());
            loginResponse.setStatus(true);
        } else {
            // 登录失败
            loginResponse.setMessage("账号密码校验失败");
            loginResponse.setStatus(false);
        }
        ctx.channel().writeAndFlush(loginResponse);
    }

    /**
     * 登录校验
     * @param loginRequest
     * @return java.lang.Integer
     * @author 彭清龙
     * @date 2019-12-24 上午 11:46
     */
    private Integer valid(LoginRequest loginRequest){
        // 查询项目是否存在
        Integer projId = loginRequest.getProjId();
        UpProjectInfo projectInfo = upProjectInfoServiceImpl.selectByPrimaryKey(projId);

        UpProjectAuthExample example = new UpProjectAuthExample();
        example.createCriteria().andAuthKeyEqualTo(loginRequest.getAuthKey());
        List<UpProjectAuth> upProjectAuths = upProjectAuthServiceImpl.selectByExample(example);
        if(upProjectAuths.size() <= 0 || projectInfo == null){
            return null;
        }
        return projectInfo.getId();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        ctx.fireChannelInactive();
    }

}
