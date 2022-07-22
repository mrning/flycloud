package com.zacboot.gateway.security.filters;

import cn.hutool.core.util.StrUtil;
import com.nimbusds.jose.JWSObject;
import com.zacboot.common.base.basebeans.exceptions.BusinessException;
import com.zacboot.common.base.constants.CommonConstant;
import com.zacboot.common.base.utils.PasswordUtil;
import com.zacboot.common.base.utils.RedisUtil;
import com.zacboot.gateway.security.service.SecurityUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 访问鉴权过滤器
 */
@Slf4j
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered, WebFilter {

    @Value("${zacboot.security.tokenKey:''}")
    private String tokenKey;
    @Value("${zacboot.security.ignore.postHttpUrls:''}")
    private String[] postIgnoreUrls;

    @Autowired
    private SecurityUserService securityUserService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isEmpty(token)) {
            return chain.filter(exchange);
        }
        try {
            //从token中解析用户信息并设置到Header中去
            String realToken = token.replace("Bearer ", "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            log.info("AuthGlobalFilter.filter() user:{}",userStr);
            ServerHttpRequest request = exchange.getRequest().mutate().header("user", userStr).build();
            exchange = exchange.mutate().request(request).build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        log.debug("请求头： {}", serverHttpRequest.getHeaders());

        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            // 记录请求的消息体
            logRequestBody(serverHttpRequest);
            handToken(chain, serverHttpRequest, exchange);
        } catch (AuthenticationException e) {
            log.error("AuthenticationException 校验过滤异常：", e);
            SecurityContextHolder.clearContext();
            throw e;
        } finally {
            stopWatch.stop();
            long usedTimes = stopWatch.getTotalTimeMillis();
            // 记录响应的消息体
            logResponseBody(serverHttpRequest, exchange.getResponse(), usedTimes);
        }

        return Mono.empty();
    }
    private void handToken(WebFilterChain filterChain, ServerHttpRequest request, ServerWebExchange exchange){
        if (new AntPathMatcher(AntPathMatcher.DEFAULT_PATH_SEPARATOR).match(postIgnoreUrls[0],request.getPath().value())){
            filterChain.filter(exchange);
        }

        // 前后端分离情况下，前端登录后将token放到请求头中，每次请求带入
        String token = request.getHeaders().getFirst(CommonConstant.REQUEST_HEADER_TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED.value(),"请求头中无token，请重新登录！");
        }
        log.debug("后台检查令牌:{}", token);

        if (StringUtils.isNotBlank(token) && PasswordUtil.verifyToken(token, tokenKey)) {
            UserDetails securityUser = securityUserService.findByUsername(String.valueOf(redisUtil.get(token))).block();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
            // 全局注入角色权限信息和登录用户基本信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.filter(exchange);
        } else {
            throw new BadCredentialsException("TOKEN无效或已过期，请重新登录！");
        }
    }

    private String logRequestBody(ServerHttpRequest request) {

        try {
            AtomicReference<String> bodyJson = new AtomicReference<>("");
            String url = request.getPath().value();
            request.getBody().subscribe(buffer -> {
                byte[] bytes = new byte[buffer.readableByteCount()];
                buffer.read(bytes);
                DataBufferUtils.release(buffer);
                bodyJson.set(new String(bytes, Charset.defaultCharset()));
                log.info("{} 接收到的参数: {}", url, bodyJson);
                CommonConstant.URL_MAPPING_MAP.put(url, url);

            });
            return bodyJson.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void logResponseBody(ServerHttpRequest request, ServerHttpResponse response, long useTime) {
        log.info("`{}`  耗时:{}ms  返回状态码: {}",CommonConstant.URL_MAPPING_MAP.get(request.getPath().value()), useTime, response.getStatusCode().value());
//        byte[] buf = response.getStatusCode().value();
//        if (buf.length > 0) {
//            String payload;
//            try {
//                payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
//            } catch (UnsupportedEncodingException ex) {
//                payload = "[unknown]";
//            }
//            log.info("`{}`  耗时:{}ms  返回的参数: {}", CommonConstant.URL_MAPPING_MAP.get(request.getRequestURI()), useTime, payload);
//        }
    }

}
