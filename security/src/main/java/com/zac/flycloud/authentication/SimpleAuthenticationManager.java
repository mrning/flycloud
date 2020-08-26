package com.zac.flycloud.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 登录请求第二步
 * 自定义认证管理器
 */
@Component
public class SimpleAuthenticationManager implements AuthenticationManager {

    private final SimpleAuthenticationProvider simpleAuthenticationProvider;

    public SimpleAuthenticationManager(SimpleAuthenticationProvider authenticationProvider) {
        this.simpleAuthenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication result = simpleAuthenticationProvider.authenticate(authentication);
        if (Objects.nonNull(result)) {
            return result;
        }
        throw new ProviderNotFoundException("Authentication failed!");
    }
}
