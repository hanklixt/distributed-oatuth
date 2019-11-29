package com.hank.security.springbootsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)  //开启方法授权 方法执行前
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     *
     * 配置用户信息服务,springsecurity会使用它来获取用户信息
     */
//    @Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
//        //准备用户数据，账号密码和权限
//        manager.createUser(User.withUsername("zhangsan").password("123").authorities("p1").build());
//        manager.createUser(User.withUsername("lisi").password("456").authorities("p2").build());
//        return manager;
//    }

    /**
     * 配置密码处理方式
     * @return
     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        //密码对比器，这里使用的是明文比对
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //密码对比器，这里使用的是明文比对
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置安全拦截机制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                //     .antMatchers("/r/**").authenticated()  // 对于/r/**资源进行拦截,要求必须登录
                .antMatchers("/r/r1").hasAuthority("p1")
                .antMatchers("/r/r2").hasAuthority("p2")  //要求资源必须登录且有对应的权限才可以访问

                .anyRequest().permitAll()           //对其余资源进行放行
                .and()
                .formLogin()
                .loginPage("/login-view")//设置默认登陆页面
                .loginProcessingUrl("/login") //设置处理登陆的接口资源
                .successForwardUrl("/login-success")//允许表单登录，且置顶登录成功后重定向资源
                .and()
                .sessionManagement()//设置session管理
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        //always 如果没有session存在就创建一个
      //  ifRequired 如果需要就创建一个Session（默认）登录时
      //   never  SpringSecurity 将不会创建Session，但是如果应用中其他地方创建了Session，那么SpringSecurity将会使用它。
      //  stateless SpringSecurity将绝对不会创建Session，也不使用Session
    }

}
