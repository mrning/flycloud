package com.zacboot.gateway.security.interceptor.filters;

import com.alibaba.fastjson.JSONObject;
import com.zacboot.common.base.utils.MultiReadRequest;
import com.zacboot.gateway.security.authentication.CustomAuthenticationManager;
import com.zacboot.gateway.security.bean.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

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
public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter implements WebFilter {

    public CustomAuthenticationProcessingFilter(CustomAuthenticationManager customAuthenticationManager, AuthenticationSuccessHandler securityLoginSuccessHandler, AuthenticationFailureHandler securityLoginFailHandler) {
        super(new AntPathRequestMatcher("/sys/login", "POST"));
        this.setAuthenticationManager(customAuthenticationManager);
        // 认证成功逻辑
        this.setAuthenticationSuccessHandler(securityLoginSuccessHandler);
        // 认证失败逻辑
        this.setAuthenticationFailureHandler(securityLoginFailHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        MultiReadRequest wrappedRequest = new MultiReadRequest(httpServletRequest);
        // 将前端传递的数据转换成jsonBean数据格式
        SysUser user = JSONObject.parseObject(wrappedRequest.getBodyJsonStrByJson(wrappedRequest), SysUser.class);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("exchange = {},chain = {}",exchange,chain);
        return null;
    }
}
