package com.lqjk.pay;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan(basePackages = {"com.lqjk.pay.mapper",
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
