package com.zac.auth;

import com.zac.feign.annotation.EnableHtxFeignClients;
import com.zac.security.annotation.EnableHtxResourceServer;
import com.zac.swagger.annotation.EnableHtxDoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@EnableHtxDoc("auth")
@EnableHtxResourceServer
@EnableHtxFeignClients(basePackages = "com.zac.request.feign")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.zac")
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
        log.info("""
                
                ----------------------------------------------------------
                        Auth Application is running!
                ----------------------------------------------------------""");

    }
}
