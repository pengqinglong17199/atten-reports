package com.quanroon.atten.reports.service.impl;

import com.quanroon.atten.reports.dao.UpPlatformAuthMapper;
import com.quanroon.atten.reports.entity.UpPlatformAuth;
import com.quanroon.atten.reports.entity.example.UpPlatformAuthExample;
import com.quanroon.atten.reports.entity.example.UpPlatmeAuthExample;
import com.quanroon.atten.reports.service.UpPlatformAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UpPlatformAuthServiceImpl implements UpPlatformAuthService {

    @Autowired private UpPlatformAuthMapper upPlatmeAuthMapper;

    @Override
    public List<UpPlatformAuth> selectByExample(UpPlatformAuthExample example) {
        return upPlatmeAuthMapper.selectByExample(example);
    }
}
