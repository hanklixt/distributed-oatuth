package com.hank.security.distributedgataway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @Description
 * @auther 李先涛
 * @create 2019-11-29 16:29
 */
@Configuration
public class ResourceServerConfig {


    public static final String RESOURCE_ID = "res1";
    /**
     * 配置 uaa资源服务
     */
    @Configuration
    @EnableResourceServer
    public class  UAAServerConfig extends ResourceServerConfigurerAdapter {

        @Autowired
        private TokenStore tokenStore;
        //
        @Override
        public void configure(ResourceServerSecurityConfigurer resources){
            //设置资源服务参数
            resources.tokenStore(tokenStore).resourceId(RESOURCE_ID)
                    .stateless(true);
        }
        //对授权服务放行
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/uaa/**").permitAll();
        }
    }

    /**
     * 配置order资源服务
     */
    public class OrderServerConfig extends ResourceServerConfigurerAdapter{
        @Autowired
        private TokenStore tokenStore;
        //
        @Override
        public void configure(ResourceServerSecurityConfigurer resources){
            //设置资源服务参数
            resources.tokenStore(tokenStore).resourceId(RESOURCE_ID)
                    .stateless(true);
        }
        //对order服务要求token中信息有对应权限
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/order/**").access("#oauth2.hasScope('ROLE_API')");
        }

    }
}
