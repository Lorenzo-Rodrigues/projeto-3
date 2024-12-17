package com.spring_boot.projeto3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Projeto3Application {

	public static void main(String[] args) {
		SpringApplication.run(Projeto3Application.class, args);
	}

}
