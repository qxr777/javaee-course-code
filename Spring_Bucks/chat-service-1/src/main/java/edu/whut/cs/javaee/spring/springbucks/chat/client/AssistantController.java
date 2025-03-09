package edu.whut.cs.javaee.spring.springbucks.chat.client;

import edu.whut.cs.javaee.spring.springbucks.chat.services.CustomerSupportAssistant;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RequestMapping("/api/assistant")
@RestController
public class AssistantController {

	private final CustomerSupportAssistant agent;

	public AssistantController(CustomerSupportAssistant agent) {
		this.agent = agent;
	}

//	@RequestMapping(path="/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	public Flux<String> chat(String chatId, String userMessage) {
//		return agent.chat(chatId, userMessage);
//	}

	@PostMapping(path = "/chat")
	public String chat(@RequestBody ChatRequest chatRequest) {
		return agent.chat(chatRequest.getChatId(), chatRequest.getUserMessage());
	}

	// 定义用于接收JSON参数的POJO类
	public static class ChatRequest {
		private String chatId;
		private String userMessage;

		// Getter和Setter方法
		public String getChatId() {
			return chatId;
		}

		public void setChatId(String chatId) {
			this.chatId = chatId;
		}

		public String getUserMessage() {
			return userMessage;
		}

		public void setUserMessage(String userMessage) {
			this.userMessage = userMessage;
		}
	}


}
