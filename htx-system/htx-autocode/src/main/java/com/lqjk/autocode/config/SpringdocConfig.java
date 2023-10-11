package com.lqjk.autocode.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("代码生成API")
                        .description("通过代码生成表结构 或者 根据表结构生成代码")
                        .version("v0.0.1"));
    }
}
