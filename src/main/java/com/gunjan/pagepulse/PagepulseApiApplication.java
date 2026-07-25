package com.gunjan.pagepulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PagepulseApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagepulseApiApplication.class, args);
	}

}
