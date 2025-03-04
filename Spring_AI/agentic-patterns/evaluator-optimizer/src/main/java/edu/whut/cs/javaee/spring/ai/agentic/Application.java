
/* 
* Copyright 2024 - 2024 the original author or authors.
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
* https://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package edu.whut.cs.javaee.spring.ai.agentic;

import edu.whut.cs.javaee.spring.ai.agentic.EvaluatorOptimizer.RefinedResponse;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// ------------------------------------------------------------
// EVALUATOR-OPTIMIZER
// ------------------------------------------------------------

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ChatClient.Builder chatClientBuilder) {
		var chatClient = chatClientBuilder.build();
		return args -> {
			RefinedResponse refinedResponse = new EvaluatorOptimizer(chatClient).loop("""
					<user input>
					用 Java 实现堆栈：
					1. push(x)
					2. pop()
					3. getMin()
					所有操作均应为 O(1)。
					所有内部字段都应是私有的，使用时应以 "this."为前缀。
					</user input>
					""");

			System.out.println("FINAL OUTPUT:\n : " + refinedResponse);
		};
	}
}
