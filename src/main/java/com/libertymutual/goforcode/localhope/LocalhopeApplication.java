package com.libertymutual.goforcode.localhope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

// TODO   To be deleted when authorization is up and running 
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class LocalhopeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalhopeApplication.class, args);
	}
}
