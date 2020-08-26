package com.zac.flycloud.interceptor.handler;

import ch.qos.logback.core.util.ContentTypeUtil;
import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSONObject;
import com.zac.flycloud.basebean.DataResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证失败处理
 */
@Component("securityLoginFailHandler")
public class SecurityLoginFailHandler extends SimpleUrlAuthenticationFailureHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("SecurityLoginFailHandler 登录认证失败！");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(DataResponseResult.error("登录认证失败")));
    }
}
