package com.zac.flycloud.config;


import com.zac.flycloud.authentication.CustomAuthenticationProvider;
import com.zac.flycloud.interceptor.filters.AuthenticationFilter;
import com.zac.flycloud.interceptor.filters.CustomAuthenticationProcessingFilter;
import com.zac.flycloud.interceptor.handler.RestAuthenticationEntryPoint;
import com.zac.flycloud.interceptor.handler.RestfulAccessDeniedHandler;
import com.zac.flycloud.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${flycloud.security.ignore.httpUrls}")
    private String[] ignoreUrls;

    @Autowired
    SecurityUserService securityUserService;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private AuthenticationFilter authenticationFilter;

    /**
     * 用户密码校验过滤器
     */
    private final CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter;

    public SecurityConfig(CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter) {
        this.customAuthenticationProcessingFilter = customAuthenticationProcessingFilter;
    }

    /**
     * security基本配置
     * 用于配置需要拦截的url路径
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 定义哪些url请求需要被保护，哪些不被保护
        http.antMatcher("/**").authorizeRequests()
                .antMatchers(ignoreUrls).permitAll() // 不限制权限的url列表
                .anyRequest()                  // 任何其他请求
                .authenticated()               // 需要身份认证
                .and().formLogin()             // 并且使用表单认证的方式
                .loginProcessingUrl("/index")  // 默认登录入口为login
                .and().csrf().disable()        // 关闭跨站请求伪造保护
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 基于token，不需要session
        // 禁用缓存
        http.headers().cacheControl();

        // 自定义过滤器认证用户名密码
        http.addFilterAt(customAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);

        // 处理登录后 token认证的逻辑
        http.addFilterBefore(authenticationFilter,CustomAuthenticationProcessingFilter.class);

        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    /**
     * 用于配置UserDetailsService及PasswordEncoder；
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).passwordEncoder(setPasswordEncoder());
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder setPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
