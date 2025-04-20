package edu.whut.cs.eadd.spring.ai.weather;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherFunctionCallingApplication implements ApplicationRunner {

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    public static void main(String[] args) {
        SpringApplication.run(WeatherFunctionCallingApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String prompt = "请问武汉今天天气如何？";

        String response = chatClientBuilder.build()
                .prompt(prompt)
                .tools(new OpenWeatherTools())
                .call()
                .content();

        System.out.println(response);
    }

}
