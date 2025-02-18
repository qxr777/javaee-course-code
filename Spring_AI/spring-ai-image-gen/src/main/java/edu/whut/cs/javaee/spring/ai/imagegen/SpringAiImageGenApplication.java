package edu.whut.cs.javaee.spring.ai.imagegen;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAiImageGenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiImageGenApplication.class, args);
    }

//    @Bean
//    ImageModel imageModel(@Value("${spring.ai.openai.api-key}") String apiKey) {
//        return new OpenAiImageModel(new OpenAiImageApi(apiKey));
//    }

}
