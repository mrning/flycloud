package com.zac.fly_cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.zac.fly_cloud.actable.mapper","com.zac.fly_cloud.mapper"})
public class FlyCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyCloudApplication.class, args);
	}

}
