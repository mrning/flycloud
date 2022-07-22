package com.zacboot.gateway.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.zacboot.common.base.basebeans.Result;
import com.zacboot.common.base.constants.CommonConstant;
import com.zacboot.common.base.utils.PasswordUtil;
import com.zacboot.common.base.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 通过认证登录成功处理
 */
@Component("securityLoginSuccessHandler")
public class SecurityLoginSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${zacboot.security.tokenKey}")
    private String tokenKey;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        log.info("SecurityLoginSuccessHandler  登录成功。");
        String userName = ((User)authentication.getPrincipal()).getUsername();
        String token = PasswordUtil.createToken(userName,tokenKey);
        redisUtil.set(userName,token, CommonConstant.TOKEN_EXPIRE_TIME);

        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add( "Content-Type","application/json; charset=UTF-8");
        response.getHeaders().add(HttpHeaders.AUTHORIZATION, token);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(
                JSONObject.toJSONString(Result.success(token,"登录成功")).getBytes(StandardCharsets.UTF_8))));
    }
}
