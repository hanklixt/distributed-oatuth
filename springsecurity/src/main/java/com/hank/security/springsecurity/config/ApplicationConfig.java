package com.hank.security.springsecurity.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * spring容器加载配置类
 */
@Configuration
@ComponentScan(basePackages = "com.hank.security.springsecurity",
excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class)})
//组件扫描,扫描spring组件到容器，过滤应存在于springmvc容器的组件
public class ApplicationConfig {
    //在此配置除了Controller的其它bean，比如：数据库链接池、事务管理器、业务bean等。


}
