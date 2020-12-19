package com.zac.flycloud.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * springfox3.0文档地址
 * http://springfox.github.io/springfox/docs/current/#configuring-springfox
 */
@EnableWebMvc
@Configuration //必须存在
@EnableOpenApi
@ComponentScan(basePackages = {"com.zac.flycloud"})
public class SwaggerConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:" + "/swagger-ui/index.html");
    }

    /**
     * app端接口文档
     *
     * @return
     */
    @Bean
    public Docket appDocket() {
        List<RequestParameter> pars = getGlobalRequestParameters();
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                // 所有以app开头的接口都显示到app接口分组里面
                .paths(input -> input.startsWith("/app"))
                .build()
                .groupName("app接口")
                .globalRequestParameters(pars)
                // 添加security登录认证
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(securityScheme()))
                .apiInfo(apiInfo());
    }

    /**
     * 后台管理页面接口文档
     *
     * @return
     */
    @Bean
    public Docket backDocket() {
        List<RequestParameter> pars = getGlobalRequestParameters();

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                // 所有以api开头的接口都显示到后台管理接口分组里面
                .paths(input -> input.startsWith("/api"))
                .build()
                .groupName("后台管理接口")
                .globalRequestParameters(pars)
                // 添加security登录认证
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(securityScheme()))
                .apiInfo(apiInfo());
    }

    private List<RequestParameter> getGlobalRequestParameters() {
        // 配置公共参数，每个请求里面都会携带
        List<RequestParameter> pars = new ArrayList<>();
        RequestParameterBuilder ticketPar = new RequestParameterBuilder();
        ticketPar.name("token").description("用户登录token")
                .in(ParameterType.HEADER)
                .accepts(Collections.singleton(MediaType.APPLICATION_JSON))
                .query(q -> q.style(ParameterStyle.LABEL)
                        .model(m -> m.scalarModel(ScalarType.STRING))
                        .defaultValue("flycloud-88888888-token"))
                .required(true).build();
        pars.add(ticketPar.build());
        return pars;
    }

    @Bean
    SecurityContext securityContext() {
        AuthorizationScope readScope = new AuthorizationScope("flycloud", "飞云信息");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = readScope;
        SecurityReference securityReference = SecurityReference.builder()
                .reference("flycloud")
                .scopes(scopes)
                .build();

        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(securityReference))
                .build();
    }

    /**
     * 配置全局参数，配置一个名为Auth的请求头
     */
    private SecurityScheme securityScheme() {
        return new ApiKey("Auth-Token", "Authorization", "header");
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
