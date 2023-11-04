package com.zac.pay;

import com.zac.swagger.annotation.EnableHtxDoc;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableHtxDoc(value = "admin")
@MapperScan(basePackages = {"com.zac.pay.mapper",
        "com.baomidou.mybatisplus.core.mapper"})
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
        log.info("""
                
                ----------------------------------------------------------
                    PayApplication is running!
                ----------------------------------------------------------\s""");
    }
}
