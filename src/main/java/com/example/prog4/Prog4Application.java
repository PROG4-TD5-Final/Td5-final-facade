package com.example.prog4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.prog4")
public class Prog4Application {
	public static void main(String[] args) {
		SpringApplication.run(Prog4Application.class, args);
	}

}
