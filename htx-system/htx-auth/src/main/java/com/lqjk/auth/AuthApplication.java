package com.lqjk.auth;

import com.lqjk.feign.annotation.EnableHtxFeignClients;
import com.lqjk.security.annotation.EnableHtxResourceServer;
import com.lqjk.swagger.annotation.EnableHtxDoc;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@EnableHtxDoc("auth")
@EnableHtxResourceServer
@EnableHtxFeignClients(basePackages = "com.lqjk.request.feign")
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
