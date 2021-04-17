package com.zac.flycloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.zac.flycloud.mapper",
		"com.zac.flycloud.actable.mapper",
		"com.baomidou.mybatisplus.core.mapper"})
public class FlyCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyCloudApplication.class, args);
	}

}
