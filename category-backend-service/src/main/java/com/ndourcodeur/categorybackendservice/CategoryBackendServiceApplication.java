package com.ndourcodeur.categorybackendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
public class CategoryBackendServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(CategoryBackendServiceApplication.class, args);
		System.out.println("Server started....");
		System.out.println("Hello Bamba Ndour !");
	}

}
