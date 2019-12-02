package com.hank.security.distributed.uaa.service;

import com.alibaba.fastjson.JSON;
import com.hank.security.distributed.uaa.dao.UserDao;
import com.hank.security.distributed.uaa.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//只需实现UserDetailService 接口，spring-security会使用获取到的用户信息和前端请求的信息进行对比
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //登录账号
        System.out.println("username="+username);

        UserDto user = userDao.getUserByUsername(username);
        if (user==null){
            return null;
        }
        String jsonString = JSON.toJSONString(user);
        //根据账号去数据库查询...
        //这里暂时使用静态数据
//        return User.withUsername(username)
//                .password(user.getPassword())
//                .authorities("p1").build();
        //拓展用户信息
        return User.withUsername(username)
                .password(user.getPassword())
                .authorities("p1").build();
    }
}
