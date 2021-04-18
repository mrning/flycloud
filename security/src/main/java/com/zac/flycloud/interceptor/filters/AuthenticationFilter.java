package com.zac.flycloud.interceptor.filters;

import com.zac.flycloud.service.SecurityUserService;
import com.zac.flycloud.utils.MultiReadRequest;
import com.zac.flycloud.utils.MultiReadResponse;
import com.zac.flycloud.utils.PasswordUtil;
import com.zac.flycloud.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.zac.flycloud.constant.CommonConstant.*;

/**
 * 访问鉴权过滤器
 */
@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityUserService securityUserService;
    @Autowired
    private RedisUtil redisUtil;

    @Value("${flycloud.security.ignore.httpUrls}")
    private String[] ignoreUrls;
    @Value("${flycloud.security.tokenKey}")
    private String tokenKey;
    @Value("${flycloud.security.sysToken}")
    private String sysToken;
    @Value("${flycloud.security.swaggerUser}")
    private String swaggerUser;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.debug("请求头类型： " + httpServletRequest.getContentType());
        if ((httpServletRequest.getContentType() != null && !httpServletRequest.getContentType().contains(REQUEST_HEADERS_JSON))
                || checkSwagger(httpServletRequest.getRequestURI())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        MultiReadRequest wrappedRequest = new MultiReadRequest(httpServletRequest);
        MultiReadResponse wrappedResponse = new MultiReadResponse(httpServletResponse);
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            // 记录请求的消息体
            logRequestBody(wrappedRequest);
            handToken(filterChain, wrappedRequest, wrappedResponse);
        } catch (AuthenticationException e) {
            log.error("AuthenticationException 校验过滤异常：", e);
            SecurityContextHolder.clearContext();
            throw e;
        } finally {
            stopWatch.stop();
            long usedTimes = stopWatch.getTotalTimeMillis();
            // 记录响应的消息体
            logResponseBody(wrappedRequest, wrappedResponse, usedTimes);
        }
    }

    private void handToken(FilterChain filterChain, MultiReadRequest wrappedRequest, MultiReadResponse wrappedResponse) throws IOException, ServletException {
        // 前后端分离情况下，前端登录后将token放到请求头中，每次请求带入
        String token = wrappedRequest.getHeader(REQUEST_HEADER_TOKEN);
        log.debug("后台检查令牌:{}", token);
        if (StringUtils.equals(token, sysToken)) {
            UserDetails securityUser = securityUserService.loadUserByUsername(swaggerUser);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
            // 全局注入角色权限信息和登录用户基本信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } else if (StringUtils.isNotBlank(token) && !PasswordUtil.verifyToken(token, tokenKey)) {
            UserDetails securityUser = securityUserService.loadUserByUsername(String.valueOf(redisUtil.get(token)));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
            // 全局注入角色权限信息和登录用户基本信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } else {
            throw new BadCredentialsException("TOKEN无效或已过期，请重新登录！");
        }
    }

    private String logRequestBody(MultiReadRequest request) {
        MultiReadRequest wrapper = request;
        if (wrapper != null) {
            try {
                String bodyJson = wrapper.getBodyJsonStrByJson(request);
                String url = wrapper.getRequestURI().replace("//", "/");
                URL_MAPPING_MAP.put(url, url);
                log.info("{} 接收到的参数: {}", url, bodyJson);
                return bodyJson;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void logResponseBody(MultiReadRequest request, MultiReadResponse response, long useTime) {
        MultiReadResponse wrapper = response;
        if (wrapper != null) {
            byte[] buf = wrapper.getBody();
            if (buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException ex) {
                    payload = "[unknown]";
                }
                log.info("`{}`  耗时:{}ms  返回的参数: {}", URL_MAPPING_MAP.get(request.getRequestURI()), useTime, payload);
            }
        }
    }

    private boolean checkSwagger(String url) {
        boolean match = false;
        for (String s : ignoreUrls) {
            if (PatternMatchUtils.simpleMatch(s, url)) {
                match = true;
            }
        }
        return match;
    }
}
