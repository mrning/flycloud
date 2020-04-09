package com.zac.fly_cloud.interceptor.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 通过认证登录成功处理
 */
@Component("securityLoginSuccessHandler")
public class SecurityLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("SecurityLoginSuccessHandler  登录成功。");
        // 认证通过后跳转到其他页面
        response.sendRedirect("index");
    }
}
