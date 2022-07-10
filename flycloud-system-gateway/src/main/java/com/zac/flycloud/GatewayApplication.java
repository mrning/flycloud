package com.zac.flycloud;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.reactive.config.EnableWebFlux;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableWebFlux
@MapperScan(basePackages = {"com.zac.flycloud.security.dao.mapper",
        "com.baomidou.mybatisplus.core.mapper"})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Gateway is running! \n" +
                "----------------------------------------------------------");
    }

}
