package com.zacboot.gateway.security.filters;

import cn.hutool.core.util.StrUtil;
import com.nimbusds.jose.JWSObject;
import com.zacboot.common.base.utils.RedisUtil;
import com.zacboot.gateway.security.service.SecurityUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * 访问鉴权过滤器
 */
@Slf4j
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

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

//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        log.debug("请求头类型： " + httpServletRequest.getContentType());
//        if ((httpServletRequest.getContentType() != null && !httpServletRequest.getContentType().contains(CommonConstant.REQUEST_HEADERS_JSON))
//                || checkSwagger(httpServletRequest.getRequestURI())) {
//            filterChain.doFilter(httpServletRequest, httpServletResponse);
//            return;
//        }
//
//        MultiReadRequest wrappedRequest = new MultiReadRequest(httpServletRequest);
//        MultiReadResponse wrappedResponse = new MultiReadResponse(httpServletResponse);
//        StopWatch stopWatch = new StopWatch();
//        try {
//            stopWatch.start();
//            // 记录请求的消息体
//            logRequestBody(wrappedRequest);
//            handToken(filterChain, wrappedRequest, wrappedResponse);
//        } catch (AuthenticationException e) {
//            log.error("AuthenticationException 校验过滤异常：", e);
//            SecurityContextHolder.clearContext();
//            throw e;
//        } finally {
//            stopWatch.stop();
//            long usedTimes = stopWatch.getTotalTimeMillis();
//            // 记录响应的消息体
//            logResponseBody(wrappedRequest, wrappedResponse, usedTimes);
//        }
//    }
//
//    private void handToken(FilterChain filterChain, MultiReadRequest wrappedRequest, MultiReadResponse wrappedResponse) throws IOException, ServletException {
//        // 前后端分离情况下，前端登录后将token放到请求头中，每次请求带入
//        String token = wrappedRequest.getHeader(CommonConstant.REQUEST_HEADER_TOKEN);
//        if (StringUtils.isBlank(token)) {
//            throw new BadCredentialsException("请求头中无token，请重新登录！");
//        }
//        log.debug("后台检查令牌:{}", token);
//
//        if (StringUtils.equals(token, sysToken)) {
//            filterChain(filterChain, wrappedRequest, wrappedResponse, swaggerUser);
//        } else if (StringUtils.isNotBlank(token) && PasswordUtil.verifyToken(token, tokenKey)) {
//            filterChain(filterChain, wrappedRequest, wrappedResponse, String.valueOf(redisUtil.get(token)));
//        } else {
//            throw new BadCredentialsException("TOKEN无效或已过期，请重新登录！");
//        }
//    }
//
//    private void filterChain(FilterChain filterChain, MultiReadRequest wrappedRequest, MultiReadResponse wrappedResponse,
//                             String userName) throws IOException, ServletException {
//        UserDetails securityUser = securityUserService.findByUsername(userName).block();
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
//        // 全局注入角色权限信息和登录用户基本信息
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        filterChain.doFilter(wrappedRequest, wrappedResponse);
//    }
//
//    private String logRequestBody(MultiReadRequest request) {
//        MultiReadRequest wrapper = request;
//        if (wrapper != null) {
//            try {
//                String bodyJson = wrapper.getBodyJsonStrByJson(request);
//                String url = wrapper.getRequestURI().replace("//", "/");
//                CommonConstant.URL_MAPPING_MAP.put(url, url);
//                log.info("{} 接收到的参数: {}", url, bodyJson);
//                return bodyJson;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    private void logResponseBody(MultiReadRequest request, MultiReadResponse response, long useTime) {
//        MultiReadResponse wrapper = response;
//        if (wrapper != null) {
//            byte[] buf = wrapper.getBody();
//            if (buf.length > 0) {
//                String payload;
//                try {
//                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
//                } catch (UnsupportedEncodingException ex) {
//                    payload = "[unknown]";
//                }
//                log.info("`{}`  耗时:{}ms  返回的参数: {}", CommonConstant.URL_MAPPING_MAP.get(request.getRequestURI()), useTime, payload);
//            }
//        }
//    }
//
//    private boolean checkSwagger(String url) {
//        boolean match = false;
//        for (String s : ignoreUrls) {
//            if (PatternMatchUtils.simpleMatch(s, url)) {
//                match = true;
//            }
//        }
//        return match;
//    }
}
