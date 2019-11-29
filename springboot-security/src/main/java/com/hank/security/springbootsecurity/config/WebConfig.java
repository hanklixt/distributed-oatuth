package com.hank.security.springbootsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("redirect:/login");    //默认Url根路径跳转到/login，此url为spring security提供
          registry.addViewController("/").setViewName("redirect:/login-view");
          registry.addViewController("/login-view").setViewName("login");   //设置url根路径跳转到自定义login视图
    }
}
