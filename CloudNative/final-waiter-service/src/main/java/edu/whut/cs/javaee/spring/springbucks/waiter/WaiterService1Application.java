package edu.whut.cs.javaee.spring.springbucks.waiter;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import edu.whut.cs.javaee.spring.springbucks.waiter.integration.Barista;
import edu.whut.cs.javaee.spring.springbucks.waiter.integration.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
@EnableDiscoveryClient
@EnableBinding({ Barista.class, Customer.class })
public class WaiterService1Application {

    public static void main(String[] args) {
        SpringApplication.run(WaiterService1Application.class, args);
    }

    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }

}
