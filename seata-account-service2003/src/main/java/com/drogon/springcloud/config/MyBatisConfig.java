package com.drogon.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.drogon.springcloud.dao"})
public class MyBatisConfig {

}

