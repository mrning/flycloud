package com.zac.flycloud.config;


import com.zac.flycloud.interceptor.CustomAuthenticationProcessingFilter;
import com.zac.flycloud.interceptor.handler.CustomExpiredSessionStrategy;
import com.zac.flycloud.properties.SecurityProperties;
import com.zac.flycloud.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security配置类
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SecurityUserService securityUserService;

    @Autowired
    CustomExpiredSessionStrategy customExpiredSessionStrategy;

    @Autowired
    SecurityProperties securityProperties;

    /**
     * 用户密码校验过滤器
     */
    private final CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter;

    public SecurityConfig(CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter) {
        this.customAuthenticationProcessingFilter = customAuthenticationProcessingFilter;
    }


    /**
     * security基本配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/sys/randomImage").permitAll();
        // 定义哪些url请求需要被保护，哪些不被保护
        http.antMatcher("/**").authorizeRequests()
                .antMatchers(securityProperties.getIgnore().getUrls()).permitAll() // 不限制权限的url列表
                .anyRequest()                  // 任何其他请求
                .authenticated()               // 需要身份认证
                .and().formLogin()             // 并且使用表单认证的方式
                .loginProcessingUrl("/index")  // 默认登录入口为login
                .and().csrf().disable()                         // 关闭跨站请求伪造保护
                .sessionManagement().maximumSessions(1)         // 同一个session的最大登录数
                .maxSessionsPreventsLogin(false)                // 当达到最大session的时候是否将旧用户提出（单点登录）
                .expiredSessionStrategy(customExpiredSessionStrategy); //当同账户登录个数达到最大值时，旧用户被踢出后的操作

        // 自定义过滤器认证用户名密码
        http.addFilterAt(customAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * 配置web资源权限
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置允许放行的资源
        web.ignoring().antMatchers(securityProperties.getIgnore().getUrls());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).passwordEncoder(setPasswordEncoder());
    }

    @Bean
    public PasswordEncoder setPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
