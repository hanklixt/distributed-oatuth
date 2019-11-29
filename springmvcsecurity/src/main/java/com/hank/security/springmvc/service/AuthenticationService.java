package com.hank.security.springmvc.service;

import com.hank.security.springmvc.model.AuthenticationRequest;
import com.hank.security.springmvc.model.UserDto;

public interface AuthenticationService {

    /**
     * 用户认证
     * @param authenticationRequest 用户认证请求
     * @return 认证成功的用户信息
     */
    UserDto authentication(AuthenticationRequest authenticationRequest);
}
