package edu.whut.cs.javaee.spring.cloud.rabbitmqbaristaservice;

import edu.whut.cs.javaee.spring.cloud.rabbitmqbaristaservice.integration.Waiter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
@EnableBinding(Waiter.class)
public class RabbitmqBaristaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqBaristaServiceApplication.class, args);
    }

}
