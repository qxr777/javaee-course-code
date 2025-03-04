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

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// ------------------------------------------------------------
// PROPMT CHAINING WORKFLOW
// ------------------------------------------------------------

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	String report = """
         第三季度业绩摘要：
			本季度我们的客户满意度得分上升至 92 分。
			与去年同期相比，收入增长了 45%。
			目前，我们在主要市场的市场份额为 23%。
			客户流失率从 8%降至 5%。
			新用户获取成本为每位用户 43 美元。
			产品采用率增至 78%。
			员工满意度达到 87 分。
			运营利润率增至 34%。
			""";

}
