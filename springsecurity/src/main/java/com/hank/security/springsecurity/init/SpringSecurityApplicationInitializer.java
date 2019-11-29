package com.hank.security.springsecurity.init;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * 创建此类提供构造方法
 * 若当前环境没有使用Spring或Spring MVC，则需要将 WebSecurityConfig(Spring Security配置类) 传入超
 *  * 类，以确保获取配置，并创建spring context。
 *
 */
public class SpringSecurityApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
    public SpringSecurityApplicationInitializer() {
      //super(WebSecurityConfig.class);
    }

}
