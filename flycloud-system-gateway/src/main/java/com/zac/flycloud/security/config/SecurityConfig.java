package com.zac.flycloud.security.config;


import com.zac.flycloud.security.interceptor.filters.AuthenticationFilter;
import com.zac.flycloud.security.interceptor.filters.CustomAuthenticationProcessingFilter;
import com.zac.flycloud.security.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Security配置类
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${flycloud.security.ignore.httpUrls}")
    private String[] ignoreUrls;

    @Autowired
    SecurityUserService securityUserService;

    @Autowired
    private AuthenticationFilter authenticationFilter;

    /**
     * 用户密码校验过滤器
     */
    private final CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter;


    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange((authorize) -> authorize
                .pathMatchers(ignoreUrls).permitAll()
                .pathMatchers("/api-admin/**").hasRole("ADMIN")
                .anyExchange().denyAll()
                .and().formLogin()
                .loginPage("/index")  // 默认登录入口为login
                .and().csrf().disable()        // 关闭跨站请求伪造保护
        );

        // 自定义过滤器认证用户名密码
        http.addFilterAt(customAuthenticationProcessingFilter,  SecurityWebFiltersOrder.AUTHENTICATION);

        // 处理登录后 token认证的逻辑
        http.addFilterBefore(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

}
