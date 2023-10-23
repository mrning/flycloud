package com.lqjk.job;

import com.lqjk.feign.annotation.EnableHtxFeignClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@EnableHtxFeignClients(basePackages = "com.lqjk.request.feign")
@SpringBootApplication
public class JobApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
        log.info("""
                
                ----------------------------------------------------------
                    JobApplication is running!
                ----------------------------------------------------------\s""");
    }
}
