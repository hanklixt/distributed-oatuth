package com.hank.security.distributedgataway.filter;

import com.alibaba.fastjson.JSON;
import com.hank.security.distributedgataway.util.EncryptUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Description
 * @auther 李先涛
 * @create 2019-11-29 17:30
 */
@Component
public class AuthFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {
        return true; //
    }

    @Override
    public String filterType() {
        return "pre";  //请求之前进行拦截
    }

    @Override
    public int filterOrder() {
        //过滤器等级
        return 0;
    }



    @Override
    public Object run() throws ZuulException {

        //获取当前用户信息
        RequestContext currentContext = RequestContext.getCurrentContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取当前用户权限信息
        if (!(authentication instanceof OAuth2Authentication)){
             //无token访问网关内的资源时，暂时只有uaa服务直接暴露
             return null;
        }
        OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
        Authentication userAuthentication = auth2Authentication.getUserAuthentication();
        Object principal = userAuthentication.getPrincipal();
        List<String> authorities = new ArrayList<String>();
        userAuthentication.getAuthorities().forEach(x->{
            authorities.add(((GrantedAuthority) x).getAuthority());
        });
        OAuth2Request oAuth2Request = auth2Authentication.getOAuth2Request();
        //把请求参数复制转发
        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();
        HashMap<String, Object> jsonToken = new HashMap<>(requestParameters);
        if (userAuthentication!=null){
            jsonToken.put("principal",userAuthentication.getName());
            jsonToken.put("authorities",authorities);
        }
        //把信息加入到json，加入到http的header中。
        currentContext.addZuulRequestHeader("json-token", EncryptUtil.encodeUTF8StringBase64(JSON.toJSONString(jsonToken)));
        return null;
    }
}
