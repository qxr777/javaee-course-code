package edu.whut.cs.javaee.spring.cloud.turbinedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
public class TurbineDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurbineDemoApplication.class, args);
	}

}
