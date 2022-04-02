package edu.whut.cs.javaee.spring.boot.greeting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@Slf4j
public class GreetingApplicationRunner implements ApplicationRunner {
    public GreetingApplicationRunner() {
        log.info("Initializing GreetingApplicationRunner.");
    }

    public void run(ApplicationArguments args) throws Exception {
        log.info("Hello everyone! We all like Spring! ");
    }
}
