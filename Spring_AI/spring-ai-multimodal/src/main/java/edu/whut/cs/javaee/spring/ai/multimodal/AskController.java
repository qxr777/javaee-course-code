package edu.whut.cs.javaee.spring.ai.multimodal;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AskController {

  private final ChatClient chatClient;

  @Value("classpath:/forecast.jpg")
  Resource forecastImageResource;

  public AskController(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

//  @PostMapping("/ask")
//  public Answer ask(@RequestBody Question question) throws Exception {
//    var answer = chatClient.prompt()
//        .user(userSpec -> userSpec
//            .text(question.question())
//            .media(MimeTypeUtils.IMAGE_JPEG, forecastImageResource))
//        .call()
//        .content();
//
//    return new Answer(answer);
//  }

  @PostMapping("/ask")
  public Answer ask(@RequestParam("image") MultipartFile image, @RequestParam String question) throws Exception {
    Media questionImage = new Media(
            MimeTypeUtils.parseMimeType("image/jpeg"), image.getResource());
    var answer = chatClient.prompt()
            .user(userSpec -> userSpec
                    .text(question)
                    .media(questionImage))
            .call()
            .content();

    return new Answer(answer);
  }

}
