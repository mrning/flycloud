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

import static com.zac.flycloud.constant.CommonConstant.REQUEST_HEADER_TOKEN;

/**
 * springfox3.0文档地址
 * http://springfox.github.io/springfox/docs/current/#configuring-springfox
 */
@EnableWebMvc
@Configuration //必须存在
@EnableOpenApi
@ComponentScan(basePackages = {"com.zac.flycloud"})
public class SwaggerConfig implements WebMvcConfigurer {

    public static final String SWAGGER_TOKEN = "flycloud-88888888-token";

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
     * 后台管理页面接口文档
     *
     * @return
     */
    @Bean
    public Docket backDocket() {
        List<RequestParameter> pars = getGlobalRequestParameters();
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.any())
                // 所有以api开头的接口都显示到后台管理接口分组里面
                .paths(input -> input.startsWith("/api"))
                .build()
                .groupName("backApi")
                .globalRequestParameters(pars)
                .apiInfo(apiInfo());
    }

    /**
     * app端接口文档
     *
     * @return
     */
    @Bean
    public Docket appDocket() {
        List<RequestParameter> pars = getGlobalRequestParameters();
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.any())
                // 所有以app开头的接口都显示到app接口分组里面
                .paths(input -> input.startsWith("/app"))
                .build()
                .groupName("appApi")
                .globalRequestParameters(pars)
                .apiInfo(apiInfo());
    }

    private List<RequestParameter> getGlobalRequestParameters() {
        // 配置公共参数，每个请求里面都会携带
        List<RequestParameter> pars = new ArrayList<>();
        RequestParameterBuilder ticketPar = new RequestParameterBuilder();
        ticketPar.name(REQUEST_HEADER_TOKEN)
                .in(ParameterType.HEADER)
                .accepts(Collections.singleton(MediaType.APPLICATION_JSON))
                .query(q -> q.style(ParameterStyle.LABEL)
                        .model(m -> m.scalarModel(ScalarType.STRING))
                        .defaultValue(SWAGGER_TOKEN))
                .required(true).build();
        pars.add(ticketPar.build());
        return pars;
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
