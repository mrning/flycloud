package com.lqjk.feign;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lqjk.feign.sentinel.ext.HtxSentinelFeign;
import com.lqjk.feign.sentinel.handle.HtxUrlBlockHandler;
import com.lqjk.feign.sentinel.parser.HtxHeaderRequestOriginParser;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * sentinel 配置
 *
 * @author lengleng
 * @date 2020-02-12
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class HtxFeignAutoConfiguration {

	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "feign.sentinel.enabled")
	public Feign.Builder feignSentinelBuilder() {
		return HtxSentinelFeign.builder();
	}

	@Bean
	@ConditionalOnMissingBean
	public BlockExceptionHandler blockExceptionHandler(ObjectMapper objectMapper) {
		return new HtxUrlBlockHandler(objectMapper);
	}

	@Bean
	@ConditionalOnMissingBean
	public RequestOriginParser requestOriginParser() {
		return new HtxHeaderRequestOriginParser();
	}

}
