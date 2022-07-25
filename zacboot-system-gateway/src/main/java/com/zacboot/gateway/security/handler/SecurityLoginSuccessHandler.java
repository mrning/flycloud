package com.zacboot.gateway.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.zacboot.common.base.basebeans.Result;
import com.zacboot.common.base.constants.CommonConstant;
import com.zacboot.common.base.utils.RedisUtil;
import com.zacboot.gateway.security.bean.SysRole;
import com.zacboot.gateway.security.bean.SysUser;
import com.zacboot.gateway.security.dao.SysUserRoleDao;
import com.zacboot.gateway.security.dao.mapper.SysUserMapper;
import com.zacboot.gateway.security.utils.JwtTool;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.BCFKSLoadStoreParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zacboot.common.base.constants.CommonConstant.*;

/**
 * 通过认证登录成功处理
 */
@Slf4j
@Component("securityLoginSuccessHandler")
public class SecurityLoginSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        log.info("SecurityLoginSuccessHandler  登录成功。");
        String userName = ((User)authentication.getPrincipal()).getUsername();
        SysUser sysUser = sysUserMapper.getUserByName(userName);

        Map<String,Object> payload = new HashMap<>();
        payload.put(JWT_PAYLOAD_USERUUID,sysUser.getUuid());
        payload.put(JWT_PAYLOAD_USERNAME,userName);
        payload.put(JWT_PAYLOAD_ROLES,sysUserRoleDao.getRolesByUserUuid(sysUser.getUuid()).stream().map(SysRole::getRoleCode).collect(Collectors.toList()));
        String token = JwtTool.createToken(payload);

        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add( "Content-Type","application/json; charset=UTF-8");
        response.getHeaders().add(REQUEST_HEADER_TOKEN, token);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(
                JSONObject.toJSONString(Result.success(token,"登录成功")).getBytes(StandardCharsets.UTF_8))));
    }
}
