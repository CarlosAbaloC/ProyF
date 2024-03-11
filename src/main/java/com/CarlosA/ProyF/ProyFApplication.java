package com.CarlosA.ProyF;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication //(exclude={DataSourceAutoConfiguration.class})
public class ProyFApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyFApplication.class, args);
	}

}
