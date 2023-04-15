package edu.whut.cs.jee.chatgpt.techsupportgpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TechSupportGptApplication implements ApplicationRunner {

    @Autowired
    private SupportSystem supportSystem;

    public static void main(String[] args) {
        SpringApplication.run(TechSupportGptApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        supportSystem.start();
    }

}
