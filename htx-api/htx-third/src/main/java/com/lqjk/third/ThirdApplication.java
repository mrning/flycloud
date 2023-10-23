package com.lqjk.third;

import com.lqjk.swagger.annotation.EnableHtxDoc;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.lqjk.third.mapper",
        "com.baomidou.mybatisplus.core.mapper"})
public class ThirdApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThirdApplication.class, args);
        log.info("""
                
                ----------------------------------------------------------
                    ThirdApplication is running!
                ----------------------------------------------------------\s""");
    }
}
