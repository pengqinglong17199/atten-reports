package com.quanroon.atten.reports.service.impl;

import com.quanroon.atten.reports.dao.UpProjectAuthMapper;
import com.quanroon.atten.reports.entity.UpProjectAuth;
import com.quanroon.atten.reports.entity.example.UpProjectAuthExample;
import com.quanroon.atten.reports.service.UpProjectAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UpProjectAuthServiceImpl implements UpProjectAuthService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired private UpProjectAuthMapper upProjectAuthMapper;

    @Override
    public List<UpProjectAuth> selectByExample(UpProjectAuthExample example) {
        return upProjectAuthMapper.selectByExample(example);
    }
    /**
     * 生成项目密钥,用于项目鉴权
     * @param projId
     * @return void
     */
    @Override
    @Transactional(readOnly = false)
    public void initAuth(Integer projId) {
        UpProjectAuthExample example = new UpProjectAuthExample();
        example.createCriteria().andProjIdEqualTo(projId);
        List<UpProjectAuth> authList = upProjectAuthMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(authList)){
            UpProjectAuth projectAuth = new UpProjectAuth();
            projectAuth.setProjId(projId);
            projectAuth.setAuthId(System.currentTimeMillis()+"");//id
            projectAuth.setAuthKey(UUID.randomUUID().toString().replaceAll("-", ""));//key
            upProjectAuthMapper.insert(projectAuth);
            logger.info("项目id={},鉴权信息已生成,鉴权密钥={}", projId, projectAuth.toString());
        }else{
            logger.info("项目id={},鉴权信息已存在,跳过!",projId);
        }
    }
}
