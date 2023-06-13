package edu.whut.cs.javaee.spring.springbucks.barista;

import edu.whut.cs.javaee.spring.springbucks.barista.integration.Waiter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
@EnableBinding(Waiter.class)
public class BaristaService1Application {

    public static void main(String[] args) {
        SpringApplication.run(BaristaService1Application.class, args);
    }

}
