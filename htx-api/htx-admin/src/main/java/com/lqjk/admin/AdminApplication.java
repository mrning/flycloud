package com.lqjk.admin;

import com.lqjk.feign.annotation.EnableHtxFeignClients;
import com.lqjk.security.annotation.EnableHtxResourceServer;
import com.lqjk.swagger.annotation.EnableHtxDoc;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;

@Slf4j
@EnableHtxDoc(value = "admin")
@EnableHtxResourceServer
@SpringBootApplication(scanBasePackages = {"com.lqjk"})
@EnableDiscoveryClient
@EnableHtxFeignClients(basePackages = "com.lqjk.request.feign")
@MapperScan(basePackages = {"com.lqjk.admin.mapper",
        "com.baomidou.mybatisplus.core.mapper"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        log.info("""
                
                ----------------------------------------------------------
                    AdminApplication Start is running!
                ----------------------------------------------------------\s""");
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
}
