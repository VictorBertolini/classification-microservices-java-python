package com.bertolini.CentralAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ClassificationApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClassificationApiApplication.class, args);
	}
}
