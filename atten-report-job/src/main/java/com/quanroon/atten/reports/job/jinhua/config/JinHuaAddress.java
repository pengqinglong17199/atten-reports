/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.jinhua.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: raojun
 * @Date: 2020/7/24 18:12
 * @Description:
 */
@Configuration
@Data
public class JinHuaAddress {
    @Value("${jinhua.url}")
    public String UPLOAD_URL;
}
