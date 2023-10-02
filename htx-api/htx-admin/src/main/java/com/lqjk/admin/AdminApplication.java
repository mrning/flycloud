package com.lqjk.admin;

import com.lqjk.security.annotation.EnablePigResourceServer;
import com.lqjk.swagger.annotation.EnablePigDoc;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.stream.Collectors;

@Slf4j
@EnablePigDoc(value = "admin")
@EnablePigResourceServer
@SpringBootApplication(scanBasePackages = {"com.lqjk"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.lqjk.request.feign")
@MapperScan(basePackages = {"com.lqjk.admin.mapper",
        "com.baomidou.mybatisplus.core.mapper"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        log.info("""
                
                ----------------------------------------------------------
                    AdminApplication Start is running!
                ----------------------------------------------------------\s""");
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
}
