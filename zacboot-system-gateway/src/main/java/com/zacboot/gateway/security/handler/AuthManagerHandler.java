package com.zacboot.gateway.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判断用户是否有权访问该接口
 */
@Component
public class AuthManagerHandler implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    MeunMapper meunMapper;

    @Autowired
    RoleMapper roleMapper;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        ServerHttpRequest request = object.getExchange().getRequest();
        String requestUrl = request.getPath().pathWithinApplication().value();
        List<MeunEntity> list = meunMapper.selectList(null);
        List<String> roles = new ArrayList<>();
        list.forEach(m -> {
            if (antPathMatcher.match(m.getPattern(), requestUrl)) {
                List<String> allRoleByMenuId = roleMapper.getAllRoleByMenuId(m.getId())
                        .stream()
                        .map(r -> r.getRole())
                        .collect(Collectors.toList());
                roles.addAll(allRoleByMenuId);
            }
        });
        if (roles.isEmpty()) {
            return Mono.just(new AuthorizationDecision(false));
        }
        return authentication
                .filter(a -> a.isAuthenticated())
                .flatMapIterable(a -> a.getAuthorities())
                .map(g -> g.getAuthority())
                .any(c -> {
                    if (roles.contains(String.valueOf(c))) {
                        return true;
                    }
                    return false;
                })
                .map(hasAuthority -> new AuthorizationDecision(hasAuthority))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return null;
    }
}

