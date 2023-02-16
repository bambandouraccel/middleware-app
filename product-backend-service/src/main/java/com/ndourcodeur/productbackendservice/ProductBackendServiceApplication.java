package com.ndourcodeur.productbackendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProductBackendServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProductBackendServiceApplication.class, args);

		System.out.println("Server started......");
	}

}
