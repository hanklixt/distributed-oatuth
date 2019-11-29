package com.hank.security.springsecurity.init;

import com.hank.security.springsecurity.config.ApplicationConfig;
import com.hank.security.springsecurity.config.WebConfig;
import com.hank.security.springsecurity.config.WebSecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//Spring容器启动时加载WebApplicationInitializer接口的所有实现类
public class SpringApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ApplicationConfig.class, WebSecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
