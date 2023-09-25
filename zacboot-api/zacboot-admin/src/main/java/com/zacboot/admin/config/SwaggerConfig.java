package com.zacboot.admin.config;


import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * springfox3.0文档地址
 * http://springfox.github.io/springfox/docs/current/#configuring-springfox
 */
@Configuration //必须存在
@EnableOpenApi
@ComponentScan(basePackages = {"com.zacboot.admin.controller"})
public class SwaggerConfig {

    /**
     * 后台管理页面接口文档
     *
     * @return
     */
    @Bean
    public Docket backDocket() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build()
                .groupName("管理后台接口")
                .securityContexts(Collections.singletonList(SecurityContext.builder().securityReferences(Collections.singletonList(new SecurityReference("token",authorizationScopes))).build()))
                .securitySchemes(Collections.singletonList(new ApiKey("token","token","header")))
                .apiInfo(apiInfo());
    }

    /**
     * api说明
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("飞云接口文档")
                .description("飞云后台 api 文档")
                .version("1.0.0")
                .build();
    }
}
