package com.spring_boot.projeto3;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class Projeto3Application {

	public static void main(String[] args) {
		SpringApplication.run(Projeto3Application.class, args);
	}

	@Bean
	MeterRegistryCustomizer<MeterRegistry>configurer(
			@Value("${spring.application.name}") String applicationName){
		return(registry)-> registry.config().commonTags("application",applicationName);
	}
}
