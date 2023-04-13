package com.zacboot.admin.work;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.zacboot"})
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.zacboot.admin.work.mapper",
        "com.baomidou.mybatisplus.core.mapper"})
public class AdminWorkApplication {

    public static void main(String[] args)  {
        SpringApplication.run(AdminWorkApplication.class, args);
        log.info("\n----------------------------------------------------------\n\t" +
                "AdminWorkApplication Start is running! \n" +
                "----------------------------------------------------------");
    }

}