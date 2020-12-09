package com.zac.flycloud.interceptor.handler;

import com.alibaba.fastjson.JSONObject;
import com.zac.flycloud.basebean.DataResponseResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 尚未登录处理
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 尚未登录处理
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(DataResponseResult.error("尚未登录，请先登录: " + authException.getMessage())));
    }
}
