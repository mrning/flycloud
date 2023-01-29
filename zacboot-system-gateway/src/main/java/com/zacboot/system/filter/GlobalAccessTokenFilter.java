package com.zacboot.system.filter;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalAccessTokenFilter implements GlobalFilter, Ordered {
    public final static String X_ACCESS_TOKEN = "X-SA-Token";

    @Value("${zacboot.security.ignore.urls}")
    private String[] ignorePaths;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();

        log.info("  access url :  "+ url);

        PathMatcher pathMatcher = new AntPathMatcher();
        // 是否需要认证
        boolean isAuth = true;
        for (String ignoreUrl : ignorePaths) {
            if (pathMatcher.match(ignoreUrl, url)) {
                isAuth = false;
            }
        }
        if (isAuth){
            StpUtil.checkLogin();
            //将现在的request，添加当前身份
            ServerHttpRequest mutableReq = exchange.getRequest().mutate().header("Authorization-UserUuid", String.valueOf(StpUtil.getLoginId())).build();
            ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
            return chain.filter(mutableExchange);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
