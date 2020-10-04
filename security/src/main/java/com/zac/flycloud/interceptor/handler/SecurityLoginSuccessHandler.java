package com.zac.flycloud.interceptor.handler;

import com.alibaba.fastjson.JSONObject;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.entity.tablemodel.SysUser;
import com.zac.flycloud.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${flycloud.tokenKey}")
    private String tokenKey;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("SecurityLoginSuccessHandler  登录成功。");
        String token = TokenUtil.createToken(((SysUser)authentication.getPrincipal()).getUsername(),tokenKey);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(DataResponseResult.success("登录成功",token)));
    }
}
