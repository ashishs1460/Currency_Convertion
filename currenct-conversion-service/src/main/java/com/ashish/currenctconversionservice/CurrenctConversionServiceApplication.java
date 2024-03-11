package com.ashish.currenctconversionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurrenctConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrenctConversionServiceApplication.class, args);
	}

}
