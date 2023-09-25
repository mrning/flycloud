package com.zacboot.autocode;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
@MapperScan(basePackages = {"com.zacboot.autocode.mapper",
        "com.baomidou.mybatisplus.core.mapper"})
public class AutoCodeApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(AutoCodeApplication.class, args);

        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        log.info("\n----------------------------------------------------------\n\t" +
                "AutoCodeApplication Start is running! Requests URLs:\n\t" +
                "Swagger文档: http://" + ip + ":" + port + "/swagger-ui/index.html\n" +
                "----------------------------------------------------------");
    }

}
