package com.hank.security.distributedgataway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @Description
 * @auther 李先涛
 * @create 2019-11-28 13:19
 */
@Configuration
public class TokenConfig {

    private static final String SIGNING_KEY = "uaa_123";

    /**
     * 定义持久化令牌的方式
     * @return
     */
    @Bean
    public TokenStore tokenStore(){
         //这里用内存来持久化
    //    return new InMemoryTokenStore();
        return new JwtTokenStore(accessTokenConverter());
    }

    //设置解析token方式，对称加密，设置key值
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY); //对称秘钥，资源服务器使用该秘钥来验证
        return converter;
    }


}
