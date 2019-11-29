package com.hank.security.springbootsecurity.service;

import com.hank.security.springbootsecurity.dao.UserDao;
import com.hank.security.springbootsecurity.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

//只需实现UserDetailService 接口，spring-security会使用获取到的用户信息和前端请求的信息进行对比
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //登录账号
        System.out.println("username="+username);

        UserDto userByUsername = userDao.getUserByUsername(username);
        if (userByUsername==null){
            return null;
        }
        //根据账号去数据库查询...
        //这里暂时使用静态数据
        return User.withUsername(username)
                .password(userByUsername.getPassword())
                .authorities("p1").build();
    }
}
