package com.zac.flycloud.aspect;

import com.zac.flycloud.service.SecurityUserService;
import com.zac.flycloud.utils.MultiReadHttpServletRequest;
import com.zac.flycloud.utils.MultiReadHttpServletResponse;
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
    @Value("${flycloud.tokenKey}")
    private String tokenKey;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.debug("请求头类型： " + httpServletRequest.getContentType());
        if ((httpServletRequest.getContentType() == null && httpServletRequest.getContentLength() > 0)
                || (httpServletRequest.getContentType() != null && !httpServletRequest.getContentType().contains(REQUEST_HEADERS_CONTENT_TYPE))) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(httpServletRequest);
        MultiReadHttpServletResponse wrappedResponse = new MultiReadHttpServletResponse(httpServletResponse);
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            // 记录请求的消息体
            logRequestBody(wrappedRequest);
            // 前后端分离情况下，前端登录后将token放到请求头中，每次请求带入
            String token = wrappedRequest.getHeader(REQUEST_HEADER);
            log.debug("后台检查令牌:{}", token);
            if (StringUtils.isNotBlank(token)) {
                // 检查token
                if (!PasswordUtil.verifyToken(token,tokenKey)) {
                    throw new BadCredentialsException("TOKEN无效或已过期，请重新登录！");
                }
                UserDetails securityUser = securityUserService.loadUserByUsername(String.valueOf(redisUtil.get(token)));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
                // 全局注入角色权限信息和登录用户基本信息
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
        } finally {
            stopWatch.stop();
            long usedTimes = stopWatch.getTotalTimeMillis();
            // 记录响应的消息体
            logResponseBody(wrappedRequest, wrappedResponse, usedTimes);
        }
    }

    private String logRequestBody(MultiReadHttpServletRequest request) {
        MultiReadHttpServletRequest wrapper = request;
        if (wrapper != null) {
            try {
                String bodyJson = wrapper.getBodyJsonStrByJson(request);
                String url = wrapper.getRequestURI().replace("//", "/");
                URL_MAPPING_MAP.put(url, url);
                log.info("`{}` 接收到的参数: {}", url, bodyJson);
                return bodyJson;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void logResponseBody(MultiReadHttpServletRequest request, MultiReadHttpServletResponse response, long useTime) {
        MultiReadHttpServletResponse wrapper = response;
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
}
