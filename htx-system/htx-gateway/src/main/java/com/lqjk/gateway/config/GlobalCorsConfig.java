package com.lqjk.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);  // 允许携带凭证（如cookies）
        config.addAllowedOrigin("*");  // 允许所有来源
        config.addAllowedMethod("*");  // 允许所有方法
        config.addAllowedHeader("*");  // 允许所有头
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}