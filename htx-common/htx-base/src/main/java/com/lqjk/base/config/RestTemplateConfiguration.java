package com.lqjk.base.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author lengleng
 * @date 2019/2/1 RestTemplate
 */
@AutoConfiguration
public class RestTemplateConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
