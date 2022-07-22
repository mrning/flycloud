package com.zacboot.gateway.security.config;

import com.zacboot.gateway.security.utils.JwtTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 解析JWT中用户信息，并授予角色权限信息
 */
@Slf4j
@Component
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    JwtTool jwtTool;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().toString();
        // 过滤路径
        if ("/auth/login".equals(path)) {
            return Mono.empty();
        }
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (StringUtils.isBlank(token)) {
            throw new DisabledException("登录失效！");
        }
        boolean isold = jwtTool.VerityToken(token);
        if (!isold) {
            throw new AccessDeniedException("登录失效！");
        }
        String username = jwtTool.getUserName(token);
        if (com.alibaba.druid.util.StringUtils.isEmpty(username)) {
            throw new AccessDeniedException("登录失效！");
        }
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(username, username);
        return ((ReactiveAuthenticationManager) authentication -> Mono.fromCallable(() -> {
            List<String> roles = jwtTool.getUserRoles(token);
            List<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            User principal = new User(username,null, null);
            return new UsernamePasswordAuthenticationToken(principal, null, authorities);
        })).authenticate(newAuthentication).map(SecurityContextImpl::new);
    }
}

