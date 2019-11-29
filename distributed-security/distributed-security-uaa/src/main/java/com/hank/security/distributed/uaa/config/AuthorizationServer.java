package com.hank.security.distributed.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author lxt
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private   TokenStore tokenStore;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AuthorizationCodeServices authorizationCodeServices;
    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 用来配置令牌端点的安全约束.
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()") //允许获取令牌
                .checkTokenAccess("permitAll()")  //允许检测令牌
                .allowFormAuthenticationForClients();  //允许表单认证
    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在
     * 这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
       /* clients.inMemory()// 使用in‐memory存储
                .withClient("c1")// client_id
                .secret(new BCryptPasswordEncoder().encode("secret"))
                .resourceIds("res1") //资源id
                .authorizedGrantTypes("authorization_code",
                        "password","client_credentials","implicit","refresh_token")// 该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .scopes("all")// 用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围
                .autoApprove(false) //false 为跳转到授权的页面，让用户进行授权，为true直接发令牌
                //加上验证回调地址
                .redirectUris("http://www.baidu.com");*/
       //使用数据库存储客户端信息
        clients.withClientDetails(clientDetailsService);
    }

//    @Bean
//    public ClientDetailsService jdbcClientDetailsService(DataSource dataSource){
//        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
//        clientDetailsService.setPasswordEncoder(passwordEncoder);
//        return clientDetailsService;
//    }

    //将客户端信息存储到数据库  注意！！！上面写法会造成子类和父类互相引用。
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

    /**
     * 用来配置令牌（token）的访问端点和令牌服务(token services)
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)//密码模式需要
                .authorizationCodeServices(authorizationCodeServices) //授权码模式需要
                .tokenServices(tokenService()) //令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST,HttpMethod.GET); //允许post,get提交
    }


    /**
     * AuthorizationServerTokenServices 接口定义了一些操作使得你可以对令牌进行一些必要的管理，令牌可以被用来
     * 加载身份信息，里面包含了这个令牌的相关权限。
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service=new DefaultTokenServices();
        service.setClientDetailsService(clientDetailsService); //客户端信息服务    设置为inmemory在内存读取，设置为jdbc在数据库读取
        service.setSupportRefreshToken(true);  //是否刷新令牌
        service.setTokenStore(tokenStore);  //设置令牌持久化方式
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();  //加强普通token，生产jwt token
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        service.setTokenEnhancer(tokenEnhancerChain);
        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
        return service;
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource){
        //  return new InMemoryAuthorizationCodeServices();
        //设置为内存中保存授权码
        return new JdbcAuthorizationCodeServices(dataSource);  //设置为mysql保存授权码
    }
}
