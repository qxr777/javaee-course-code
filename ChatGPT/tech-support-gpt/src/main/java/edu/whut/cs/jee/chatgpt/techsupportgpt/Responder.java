package edu.whut.cs.jee.chatgpt.techsupportgpt;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * The responder class represents a response generator object.
 * It is used to generate an automatic response, based on specified input.
 * Input is presented to the responder as a set of words, and based on those
 * words the responder will generate a String that represents the response.
 *
 * Internally, the reponder uses a HashMap to associate words with response
 * strings and a list of default responses. If any of the input words is found
 * in the HashMap, the corresponding response is returned. If none of the input
 * words is recognized, one of the default responses is randomly chosen.
 *
 * @author     Michael Kölling and David J. Barnes
 * @version    1.0 (2016.02.29)
 */
@Component
public class Responder
{
    @Value("${openai.api.key}")
    private String apiKey;
    private OpenAiService service;
    private static Logger logger = LoggerFactory.getLogger(Responder.class);

    /**
     * Construct a Responder
     */
    public Responder()
    {

    }

    /**
     * 同步调用openai api得到回答的内容
     * @param content
     * @return
     */
//    public String generateResponse(String content)
//    {
//        if(service == null) {
//            service = new OpenAiService(apiKey);
//        }
//        ChatMessage chatMessage = new ChatMessage("user", content);
//        List<ChatMessage> messageList = new ArrayList<ChatMessage>();
//        messageList.add(chatMessage);
//        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
//                .model("gpt-3.5-turbo")
//                .build();
//        chatCompletionRequest.setMessages(messageList);
////        service.createChatCompletion(chatCompletionRequest).getChoices().forEach(System.out::println);
//        try{
//            StringBuffer sb = new StringBuffer();
//            service.createChatCompletion(chatCompletionRequest).getChoices().forEach(item -> sb.append(item.getMessage().getContent()));
//            return sb.toString();
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }

    /**
     * 异步调用openai api得到回答的内容
     * @param inputContent
     * @return
     */
    public String generateResponse(String inputContent) {
        if(service == null) {
            service = new OpenAiService(apiKey);
        }
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), inputContent);
        messages.add(systemMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(500)
                .logitBias(new HashMap<>())
                .build();

        StringBuffer sb = new StringBuffer();
        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(item -> item.getChoices().forEach(item1 -> System.out.print(item1.getMessage().getContent())));
//        service.shutdownExecutor();
        return sb.toString();
    }

}
