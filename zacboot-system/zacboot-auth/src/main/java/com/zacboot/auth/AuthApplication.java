package com.zacboot.auth;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@EnableFeignClients(basePackages = "com.zacboot.auth.feign")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.zacboot")
@MapperScan(basePackages = {"com.zacboot.auth.mapper",
        "com.baomidou.mybatisplus.core.mapper"})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
        log.info("\n----------------------------------------------------------\n\t" +
                "Auth Application is running! \n" +
                "----------------------------------------------------------");

    }
}
