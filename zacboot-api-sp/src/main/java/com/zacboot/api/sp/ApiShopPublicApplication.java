package com.zacboot.api.sp;

import lombok.extern.slf4j.Slf4j;
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
@EnableFeignClients(basePackages = "com.zacboot.api.sp.feign")
public class ApiShopPublicApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiShopPublicApplication.class, args);
        log.info("\n----------------------------------------------------------\n\t" +
                "ApiShopPublicApplication Start is running! \n" +
                "----------------------------------------------------------");
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
}