package edu.whut.cs.javaee.spring.ai.agentic;

import edu.whut.cs.javaee.spring.ai.agentic.ChainWorkflow;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

@Service
public class AiServiceImpl implements AiService {

  private final ChainWorkflow chainWorkflow;

  public AiServiceImpl(ChatClient.Builder chatClientBuilder) {
    ChatClient chatClient = chatClientBuilder
        .defaultAdvisors(
            new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
        .build();
    chainWorkflow = new ChainWorkflow(chatClient);
  }

  @Override
  public String complete(String message) {
    return chainWorkflow.chain(message);
  }

}
