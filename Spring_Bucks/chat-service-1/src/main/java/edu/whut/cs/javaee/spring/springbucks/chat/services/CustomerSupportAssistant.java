/*
 * Copyright 2024-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.whut.cs.javaee.spring.springbucks.chat.services;

import java.time.LocalDate;

import edu.whut.cs.javaee.spring.springbucks.chat.integration.CoffeeOrderService;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * * @author Christian Tzolov
 */
@Service
public class CustomerSupportAssistant {

	private final ChatClient chatClient;

//	@Autowired
//	private final CoffeeTools coffeeTools;

	public CustomerSupportAssistant(ChatClient.Builder modelBuilder, VectorStore vectorStore, ChatMemory chatMemory, CoffeeOrderService coffeeOrderService) {

		// @formatter:off
		this.chatClient = modelBuilder
				.defaultSystem("""
					   你是一家名为 "SpringBucks"的咖啡连锁店的客户支持代理。不要谈论与本店业务无关的任何话题。
					   允许你依据SpringBucks价格表，告知客户咖啡的价格。
					   在提供有关预订或取消预订的信息之前，您必须始终进行检查：订单编号,
                    今天的日期是{{current_date}}.
					""")
				.defaultAdvisors(
						new PromptChatMemoryAdvisor(chatMemory), // Chat Memory
						// new VectorStoreChatMemoryAdvisor(vectorStore)),
					
						new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().topK(4).similarityThresholdAll().build()), // RAG
						// new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()
						// 	.withFilterExpression("'documentType' == 'terms-of-service' && region in ['EU', 'US']")),
						
						new LoggingAdvisor())

//				.defaultFunctions("getBookingDetails", "changeBooking", "cancelBooking") // FUNCTION CALLING
//				.defaultFunctions("getBookingDetails", "bookingCoffee") // FUNCTION CALLING
				.defaultTools(new CoffeeTools(coffeeOrderService))
//				.defaultTools(coffeeTools)

				.build();
		// @formatter:on
	}

//	public Flux<String> chat(String chatId, String userMessageContent) {
//
//		return this.chatClient.prompt()
//			.system(s -> s.param("current_date", LocalDate.now().toString()))
//			.user(userMessageContent)
//			.advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId).param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
//			.stream()
//			.content();
//	}

	public String chat(String chatId, String userMessageContent) {
		return this.chatClient.prompt()
				.system(s -> s.param("current_date", LocalDate.now().toString()))
				.user(userMessageContent)
				.advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId).param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
//				.tools(coffeeTools)
				.call()
				.content();
	}


}