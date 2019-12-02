package com.hank.security.distributed.order.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hank.security.distributed.order.model.UserDTO;
import com.hank.security.distributed.order.util.EncryptUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @Description
 * @auther 李先涛
 * @create 2019-12-02 10:46
 * 解析token 并把用户信息设置到当前线程上下文
 */
@Component
public class TokenAuthenticationFilter  extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("json-token");
        if (!StringUtils.isEmpty(token)){
            String json = EncryptUtil.decodeUTF8StringBase64(token);
            JSONObject jsonObject = JSONObject.parseObject(json);
            String principal= jsonObject.getString("principal");
            UserDTO userDTO=new UserDTO();
            userDTO.setUsername(principal);
            JSONArray authorities = jsonObject.getJSONArray("authorities");
            String[] auth =  authorities.toArray(new String[authorities.size()]);
            //2.新建并填充authentication
            UsernamePasswordAuthenticationToken authentication = new
                    UsernamePasswordAuthenticationToken(
                    userDTO, null, AuthorityUtils.createAuthorityList(auth));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                    httpServletRequest));
            //3.将authentication保存进安全上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
