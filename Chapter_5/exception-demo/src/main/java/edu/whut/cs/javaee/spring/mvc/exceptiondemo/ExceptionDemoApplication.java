package edu.whut.cs.javaee.spring.mvc.exceptiondemo;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
public class ExceptionDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExceptionDemoApplication.class, args);
	}

	@Bean
	public Hibernate5Module hibernate5Module() {
		return new Hibernate5Module();
	}

//	@Bean
//	public Jackson2ObjectMapperBuilderCustomizer jacksonBuilderCustomizer() {
//		return builder -> builder.indentOutput(true);
//	}
}
