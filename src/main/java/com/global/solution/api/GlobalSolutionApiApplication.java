package com.global.solution.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GlobalSolutionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobalSolutionApiApplication.class, args);
	}

}
