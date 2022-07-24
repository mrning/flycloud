package com.zacboot.gateway.security.handler;

import com.zacboot.gateway.security.bean.SysRole;
import com.zacboot.gateway.security.bean.SysUser;
import com.zacboot.gateway.security.dao.SysUserRoleDao;
import com.zacboot.gateway.security.dao.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判断用户是否有权访问该接口
 */
@Component
public class AuthManagerHandler implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        ServerHttpRequest request = object.getExchange().getRequest();
        SysUser sysUser = sysUserMapper.getUserByName("zacboot");
        List<String> roles = sysUserRoleDao.getRolesByUserUuid(sysUser.getUuid()).stream().map(SysRole::getUuid).collect(Collectors.toList());

        if (roles.isEmpty()) {
            return Mono.just(new AuthorizationDecision(false));
        }
        return authentication
                .filter(a -> a.isAuthenticated())
                .flatMapIterable(a -> {
                    System.out.println(a);
                    return a.getAuthorities();
                })
                .map(g -> {
                    System.out.println(g);
                    return g.getAuthority();
                })
                .any(c -> {
                    System.out.println(c);
                    return roles.contains(String.valueOf(c));
                })
                .map(hasAuthority -> new AuthorizationDecision(hasAuthority))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return Mono.empty();
    }
}

