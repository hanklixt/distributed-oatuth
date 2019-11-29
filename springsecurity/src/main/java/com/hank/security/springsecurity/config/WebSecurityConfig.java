package com.hank.security.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     *
     * 配置用户信息服务,springsecurity会使用它来获取用户信息
     */
    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
        //准备用户数据，账号密码和权限
        manager.createUser(User.withUsername("zhangsan").password("123").authorities("p1").build());
        manager.createUser(User.withUsername("lisi").password("456").authorities("p2").build());
        return manager;
    }

    /**
     * 配置密码处理方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //密码对比器，这里使用的是明文比对
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 配置安全拦截机制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
           //     .antMatchers("/r/**").authenticated()  // 对于/r/**资源进行拦截,要求必须登录
                .antMatchers("/r/r1").hasAuthority("p1")
                .antMatchers("/r/r2").hasAuthority("p2")  //要求资源必须登录且有对应的权限才可以访问

                .anyRequest().permitAll()           //对其余资源进行放行
                .and()
                .formLogin().successForwardUrl("/login-success"); //允许表单登录，且置顶登录成功后重定向资源

    }
}
