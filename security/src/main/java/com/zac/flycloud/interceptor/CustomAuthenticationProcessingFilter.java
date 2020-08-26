package com.zac.flycloud.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zac.flycloud.authentication.SimpleAuthenticationManager;
import com.zac.flycloud.entity.tablemodel.SysUser;
import com.zac.flycloud.utils.MultiReadHttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 发送请求第一步拦截
 * 处理登录拦截器
 * 处理http请求和响应对象
 * 介绍：https://www.jianshu.com/p/97ce9b071505
 */
@Slf4j
@Component
public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationProcessingFilter(SimpleAuthenticationManager simpleAuthenticationManager, AuthenticationSuccessHandler securityLoginSuccessHandler, AuthenticationFailureHandler securityLoginFailHandler) {
        super(new AntPathRequestMatcher("/sys/login", "POST"));
        this.setAuthenticationManager(simpleAuthenticationManager);
        // 认证成功逻辑
        this.setAuthenticationSuccessHandler(securityLoginSuccessHandler);
        // 认证失败逻辑
        this.setAuthenticationFailureHandler(securityLoginFailHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(httpServletRequest);
        // 将前端传递的数据转换成jsonBean数据格式
        SysUser user = JSONObject.parseObject(wrappedRequest.getBodyJsonStrByJson(wrappedRequest), SysUser.class);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
