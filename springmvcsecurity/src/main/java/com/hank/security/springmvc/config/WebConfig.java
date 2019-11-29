package com.hank.security.springmvc.config;

import com.hank.security.springmvc.interceptor.SimpleAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//springmvc 加载配置类
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.hank.security.springmvc",includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,
value = Controller.class)})
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    SimpleAuthenticationInterceptor authenticationInterceptor;
    /**
     * 定义视图解析器
     * @return
     */
    @Bean
    public InternalResourceViewResolver viewResolver(){
      InternalResourceViewResolver viewResolver = new  InternalResourceViewResolver();
      viewResolver.setPrefix("/WEB-INF/view/");
      viewResolver.setSuffix(".jsp");
      return viewResolver;
  }

    /**
     * 将/直接导向login.jsp页面
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }

    /**
     * 注入mvc拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/r/**");
    }
}
