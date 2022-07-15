package com.zacboot.gateway.security.authentication;

import com.zacboot.gateway.security.service.SecurityUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 登录请求第三步拦截
 * 自定义认证处理
 */
@Slf4j
@Component
public class CustomAuthenticationManager extends UserDetailsRepositoryReactiveAuthenticationManager {

    @Autowired
    SecurityUserService securityUserService;

    public CustomAuthenticationManager(ReactiveUserDetailsService userDetailsService) {
        super(userDetailsService);
    }

    /**
     * 如果验证成功，则返回authentication。如果无法确定身份验证，则返回一个空的Mono。如果认证失败，返回Mono错误。
     * @param username The username to retrieve
     * @return
     */
    @Override
    protected Mono<UserDetails> retrieveUser(String username) {
        return securityUserService.findByUsername(username);
    }

}
