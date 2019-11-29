package com.hank.security.springsecurity.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//springmvc 加载配置类
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.hank.security.springsecurity",includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,
value = Controller.class)})
public class WebConfig implements WebMvcConfigurer {

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
        //默认访问工程跳到/login,此url由springsecurity提供
        registry.addViewController("/").setViewName("redirect:/login");
    }


}
