package com.zacboot.system.sso;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@Slf4j
@EnableFeignClients(basePackages = "com.zacboot.system.sso.feign")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.zacboot.system")
@MapperScan(basePackages = {"com.zacboot.system.sso.mapper",
        "com.baomidou.mybatisplus.core.mapper"})
public class SsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
        log.info("\n----------------------------------------------------------\n\t" +
                "SSO Application is running! \n" +
                "----------------------------------------------------------");
    }

}
