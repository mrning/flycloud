package com.zac.fly_cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zac.fly_cloud"})
public class FlyCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyCloudApplication.class, args);
	}

}
