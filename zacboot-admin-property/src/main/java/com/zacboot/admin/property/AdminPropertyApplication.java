package com.zacboot.admin.property;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.zacboot"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zacboot.admin.property.feign")
@MapperScan(basePackages = {"com.zacboot.admin.property.mapper",
        "com.baomidou.mybatisplus.core.mapper"})
public class AdminPropertyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminPropertyApplication.class, args);
        log.info("\n----------------------------------------------------------\n\t" +
                "AdminPropertyApplication Start is running! \n" +
                "----------------------------------------------------------");
    }
}
