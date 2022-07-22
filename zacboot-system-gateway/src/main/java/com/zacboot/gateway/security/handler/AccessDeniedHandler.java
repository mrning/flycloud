package com.zacboot.gateway.security.handler;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

import java.io.UnsupportedEncodingException;

/**
 * 无权访问的提示Handler
 */
@Component
public class AccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
        JSONObject params = new JSONObject();
        params.put("code", 403);
        params.put("msg", "权限不足！");

        ServerHttpResponse response = serverWebExchange.getResponse();

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        Mono<Void> ret = null;
        try {
            ret = response.writeAndFlushWith(Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(params.toJSONString().getBytes("UTF-8")))));
        } catch (UnsupportedEncodingException e0) {
            e0.printStackTrace();
        }
        return ret;
    }
}

