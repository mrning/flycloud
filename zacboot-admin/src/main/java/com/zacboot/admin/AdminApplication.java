package com.zacboot.admin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.zacboot"})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = {"com.zacboot.admin.dao.mapper",
        "com.baomidou.mybatisplus.core.mapper"})
public class AdminApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(AdminApplication.class, args);

        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        log.info("\n----------------------------------------------------------\n\t" +
                "AdminApplication Start is running! Requests URLs:\n\t" +
                "Swagger文档: http://" + ip + ":" + port + "/swagger-ui/index.html\n" +
                "----------------------------------------------------------");
    }
}
