package com.zacboot.gateway.security.filters;

import com.zacboot.common.base.basebeans.exceptions.BusinessException;
import com.zacboot.common.base.constants.CommonConstant;
import com.zacboot.common.base.utils.RedisUtil;
import com.zacboot.gateway.security.service.SecurityUserService;
import com.zacboot.gateway.security.utils.JwtTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 访问鉴权过滤器
 */
@Slf4j
@Component
public class AuthenticationFilter implements Ordered, WebFilter {

    @Value("${zacboot.security.tokenKey:''}")
    private String tokenKey;
    @Value("${zacboot.security.ignore.postHttpUrls:''}")
    private String[] postIgnoreUrls;

    @Autowired
    private SecurityUserService securityUserService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        log.debug("[{}]-- 请求头： {}", serverHttpRequest.getPath().value(), serverHttpRequest.getHeaders());

        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            // 记录请求的消息体
            logRequestBody(serverHttpRequest);
            handToken(serverHttpRequest);
        } catch (AuthenticationException e) {
            log.error("AuthenticationException 校验过滤异常：", e);
            SecurityContextHolder.clearContext();
            throw e;
        } finally {
            stopWatch.stop();
            long usedTimes = stopWatch.getTotalTimeMillis();
            log.info("[{}]-- 请求耗时:{}ms",serverHttpRequest.getPath().value(), usedTimes);
            chain.filter(exchange);
        }

        return Mono.empty();
    }
    private void handToken(ServerHttpRequest request){
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径直接放行
        List<String> ignoreUrls = Arrays.stream(postIgnoreUrls).toList();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                return;
            }
        }
        //对应跨域的预检请求直接放行
        if(request.getMethod()== HttpMethod.OPTIONS){
            return;
        }
        // 前后端分离情况下，前端登录后将token放到请求头中，每次请求带入
        String token = request.getHeaders().getFirst(CommonConstant.REQUEST_HEADER_TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED.value(),"请求头中无token，请重新登录！");
        }
        log.debug("后台检查令牌:{}", token);

        SecretKey key = (SecretKey)redisUtil.hget(CommonConstant.PREFIX_USER_TOKEN,token);
        if (StringUtils.isNotBlank(token) && JwtTool.verityToken(token, key)) {
            UserDetails securityUser = securityUserService.findByUsername(JwtTool.getUserName(token,key)).block();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
            // 全局注入角色权限信息和登录用户基本信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
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

}
