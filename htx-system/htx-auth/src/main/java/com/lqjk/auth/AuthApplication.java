package com.lqjk.auth;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@EnableFeignClients(basePackages = "com.lqjk.request.feign")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.lqjk")
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
        log.info("""
                
                ----------------------------------------------------------
                        Auth Application is running!
                ----------------------------------------------------------""");

    }
}
