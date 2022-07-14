package com.zacboot.gateway.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.zacboot.common.base.utils.PasswordUtil;
import com.zacboot.common.base.basebeans.Result;
import com.zacboot.gateway.security.bean.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 通过认证登录成功处理
 */
@Component("securityLoginSuccessHandler")
public class SecurityLoginSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${zacboot.security.tokenKey}")
    private String tokenKey;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        log.info("SecurityLoginSuccessHandler  登录成功。");
        String token = PasswordUtil.createToken(((SysUser)authentication.getPrincipal()).getUsername(),tokenKey);

        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add( "Content-Type","application/json; charset=UTF-8");
        return response.writeWith(Mono.just(response.bufferFactory().wrap(
                JSONObject.toJSONString(Result.success("登录成功",token)).getBytes(StandardCharsets.UTF_8))));
    }
}
