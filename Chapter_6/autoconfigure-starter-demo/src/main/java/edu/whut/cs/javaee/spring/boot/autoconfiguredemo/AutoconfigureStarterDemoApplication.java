package edu.whut.cs.javaee.spring.boot.autoconfiguredemo;

import edu.whut.cs.javaee.spring.boot.greeting.GreetingApplicationRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoconfigureStarterDemoApplication implements CommandLineRunner {

    @Autowired
    GreetingApplicationRunner greetingApplicationRunner;

    public static void main(String[] args) {
        SpringApplication.run(AutoconfigureStarterDemoApplication.class, args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        greetingApplicationRunner.greet();
    }
}
