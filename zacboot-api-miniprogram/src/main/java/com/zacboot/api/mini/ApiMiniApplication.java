package com.zacboot.api.mini;

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
@SpringBootApplication(scanBasePackages = {"com.zacboot"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zacboot.api.mini.feign")
@MapperScan(basePackages = {"com.zacboot.api.mini.mapper",
        "com.baomidou.mybatisplus.core.mapper"})
public class ApiMiniApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiMiniApplication.class, args);
        log.info("\n----------------------------------------------------------\n\t" +
                "ApiMiniApplication Start is running! \n" +
                "----------------------------------------------------------");
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
}