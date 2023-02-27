package com.mywork.goohaetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class GoohaetestApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoohaetestApplication.class, args);
	}

}
