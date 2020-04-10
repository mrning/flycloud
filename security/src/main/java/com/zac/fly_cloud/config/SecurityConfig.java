package com.zac.fly_cloud.config;


import com.zac.fly_cloud.interceptor.SecurityAccessDecision;
import com.zac.fly_cloud.interceptor.SecurityFilterInvocatioin;
import com.zac.fly_cloud.interceptor.handler.CustomExpiredSessionStrategy;
import com.zac.fly_cloud.properties.SecurityProperties;
import com.zac.fly_cloud.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * Security配置类
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationSuccessHandler securityLoginSuccessHandler;

    @Autowired
    AuthenticationFailureHandler securityLoginFailHandler;

    @Autowired
    SecurityAccessDecision securityAccessDecision;

    @Autowired
    SecurityFilterInvocatioin securityFilterInvocatioin;

    @Autowired
    SecurityUserService securityUserService;

    @Autowired
    CustomExpiredSessionStrategy customExpiredSessionStrategy;

    @Autowired
    SecurityProperties securityProperties;

    /**
     * security基本配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 定义哪些url请求需要被保护，哪些不被保护
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        // 设置 获取请求url的所有权限
                        o.setSecurityMetadataSource(securityFilterInvocatioin);
                        // 设置 获取请求url需要的权限当前登录用户时候拥有
                        o.setAccessDecisionManager(securityAccessDecision);
                        return o;
                    }
                })
                .antMatchers(securityProperties.getIgnore().getUrls()).permitAll()
                .anyRequest()                  // 任何其他请求
                .authenticated()               // 需要身份认证
                .and().formLogin()             // 并且使用表单认证的方式
                .loginProcessingUrl("/login")  // 默认登录入口为login
                .successHandler(securityLoginSuccessHandler)    // 设置认证成功处理逻辑
                .failureHandler(securityLoginFailHandler)       // 设置认证失败处理逻辑
                .and().csrf().disable()                         // 关闭跨站请求伪造保护
                .sessionManagement().maximumSessions(1)         // 同一个session的最大登录数
                .maxSessionsPreventsLogin(false)                // 当达到最大session的时候是否将旧用户提出（单点登录）
                .expiredSessionStrategy(customExpiredSessionStrategy); //当达到最大值时，旧用户被踢出后的操作

    }

    /**
     * 配置web资源权限
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置允许放行的资源
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).passwordEncoder(setPasswordEncoder());
    }

    @Bean
    public PasswordEncoder setPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
