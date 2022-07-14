package com.zacboot.gateway.security.authentication;

import com.alibaba.fastjson.JSONObject;
import com.zacboot.common.base.basebeans.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        ServerHttpResponse response = exchange.getResponse();
        log.debug("暂未登录！");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add( "Content-Type","application/json; charset=UTF-8");
        return response.writeWith(Mono.just(response.bufferFactory().wrap(
                JSONObject.toJSONString(Result.error("暂未登录，请先登录。" + ex.getMessage())).getBytes(StandardCharsets.UTF_8))));
    }
}
