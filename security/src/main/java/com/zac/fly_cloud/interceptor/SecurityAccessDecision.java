package com.zac.fly_cloud.interceptor;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * 权限决策管理器
 */
@Component
public class SecurityAccessDecision implements AccessDecisionManager {

    /**
     * 获取当前用户权限和请求url需要的权限做对比，决定是否认证通过
     * @param authentication 包含了当前的用户信息，包括拥有的权限,即之前UserDetailsService登录时候存储的用户对象
     * @param o 就是FilterInvocation对象，可以得到request等web资源。
     * @param collection 是本次访问需要的权限。即上一步的 MyFilterInvocationSecurityMetadataSource 中查询核对得到的权限列表
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        Iterator<ConfigAttribute> iterator = collection.iterator();
        // 遍历已经获取到的请求url需要的所有权限
        while (iterator.hasNext()) {
            // 如果请求url未配置任何权限
            if (authentication == null) {
                throw new AccessDeniedException("当前访问没有权限");
            }
            ConfigAttribute ca = iterator.next();
            // 当前请求需要的权限
            String needRole = ca.getAttribute();
            // 如果返回了特殊权限
            if ("ROLE_LOGIN".equals(needRole)) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new BadCredentialsException("未登录");
                } else
                    return;
            }
            //当前用户所具有的权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
