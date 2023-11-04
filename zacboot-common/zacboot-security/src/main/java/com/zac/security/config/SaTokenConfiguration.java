package com.zac.security.config;

import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

/**
 * [Sa-Token 权限认证] 全局配置类
 */
@Slf4j
@Configuration
public class SaTokenConfiguration {

    @Value("${zacboot.security.ignore.urls:''}")
    private String[] ignorePaths;

    /**
     * 注册 [Sa-Token全局过滤器]
     */
    @Bean
    public SaServletFilter getSaReactorFilter() {
        return new SaServletFilter()
                // 指定 [拦截路由]
                .addInclude("/**")
                // 指定 [放行路由]
                .addExclude(ignorePaths)
                // 指定[异常处理函数]：每次[认证函数]发生异常时执行此函数
                .setError(e -> {
                    log.error("---------- sa全局异常 ----------",e);
                    return SaResult.error(e.getMessage());
                });
    }
}
