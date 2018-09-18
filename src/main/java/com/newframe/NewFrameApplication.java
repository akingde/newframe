package com.newframe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewFrameApplication {

	public static void main(String[] args) {

		SpringApplication.run(NewFrameApplication.class, args);
	}
}
