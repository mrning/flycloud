package com.zacboot.gateway.security.config;


import com.zacboot.gateway.security.authentication.CustomAuthenticationManager;
import com.zacboot.gateway.security.authentication.CustomerAuthenticationEntryPoint;
import com.zacboot.gateway.security.handler.SecurityLoginFailHandler;
import com.zacboot.gateway.security.handler.SecurityLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

/**
 * Security配置类
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${zacboot.security.ignore.httpUrls:''}")
    private String[] ignoreUrls;

    @Autowired
    private SecurityLoginSuccessHandler securityLoginSuccessHandler;

    @Autowired
    private SecurityLoginFailHandler securityLoginFailHandler;

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    private CustomerAuthenticationEntryPoint customerAuthenticationEntryPoint;

    /**
     * app请求 安全配置
     * @param http
     * @return
     */
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity http) {
        http.securityMatcher(new PathPatternParserServerWebExchangeMatcher("/api-app/**"))
                .authorizeExchange((exchanges) -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyExchange().authenticated()
                ).formLogin(formLogin -> formLogin.loginPage("/api-app/app/sys/login")
                        .authenticationEntryPoint(customerAuthenticationEntryPoint)
                        .authenticationManager(customAuthenticationManager)
                        .authenticationSuccessHandler(securityLoginSuccessHandler)
                        .authenticationFailureHandler(securityLoginFailHandler)).csrf().disable();
        return http.build();
    }

    /**
     * admin管理后台 安全配置
     * @param http
     * @return
     */
    @Bean
    SecurityWebFilterChain adminHttpSecurity(ServerHttpSecurity http) {
        http.securityMatcher(new PathPatternParserServerWebExchangeMatcher("/api-admin/**"))
            .authorizeExchange((authorize) -> authorize
                .pathMatchers(ignoreUrls).permitAll()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange().authenticated())
            .formLogin(formLogin -> formLogin.loginPage("/api-admin/admin/sys/login")
                    .authenticationEntryPoint(customerAuthenticationEntryPoint)
                    .authenticationManager(customAuthenticationManager) //登录认证管理
                    .authenticationSuccessHandler(securityLoginSuccessHandler)  // 认证成功处理
                    .authenticationFailureHandler(securityLoginFailHandler)).csrf().disable();     // 认证失败处理
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
