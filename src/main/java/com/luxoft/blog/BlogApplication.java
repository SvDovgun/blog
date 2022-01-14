package com.luxoft.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication{

	public static void main(String[] args) {
		System.out.println("in main class");
		SpringApplication.run(BlogApplication.class, args);
	}
}