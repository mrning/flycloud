package com.zacboot.gateway.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.zacboot.common.base.basebeans.Result;
import com.zacboot.gateway.security.authentication.CustomerAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 登录认证失败处理
 */
@Slf4j
@Component("securityLoginFailHandler")
public class SecurityLoginFailHandler extends ServerAuthenticationEntryPointFailureHandler {

    public SecurityLoginFailHandler(CustomerAuthenticationEntryPoint customerAuthenticationEntryPoint) {
        super(customerAuthenticationEntryPoint);
    }

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        log.info("SecurityLoginFailHandler 登录认证失败！");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add( "Content-Type","application/json; charset=UTF-8");
        return response.writeWith(Mono.just(response.bufferFactory().wrap(
                JSONObject.toJSONString(Result.error("登录认证失败。" + exception.getMessage())).getBytes(StandardCharsets.UTF_8))));
    }
}
