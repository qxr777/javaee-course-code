package edu.whut.cs.javaee.spring.cloud.gitconfigwaiterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
@EnableDiscoveryClient
public class GitConfigWaiterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitConfigWaiterServiceApplication.class, args);
    }

}
