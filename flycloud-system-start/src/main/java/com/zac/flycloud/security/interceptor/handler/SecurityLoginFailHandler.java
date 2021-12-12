package com.zac.flycloud.security.interceptor.handler;

import com.alibaba.fastjson.JSONObject;
import com.zac.flycloud.bean.basebean.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Slf4j
@Component("securityLoginFailHandler")
public class SecurityLoginFailHandler extends SimpleUrlAuthenticationFailureHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     *
     * @param request
     * @param response
     * @param exception 登录失败的异常原因
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.debug("SecurityLoginFailHandler 登录认证失败！");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(Result.error("登录认证失败: " + exception.getMessage())));
    }
}
