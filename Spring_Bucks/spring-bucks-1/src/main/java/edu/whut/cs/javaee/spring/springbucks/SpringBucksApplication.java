package edu.whut.cs.javaee.spring.springbucks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
public class SpringBucksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}

}

