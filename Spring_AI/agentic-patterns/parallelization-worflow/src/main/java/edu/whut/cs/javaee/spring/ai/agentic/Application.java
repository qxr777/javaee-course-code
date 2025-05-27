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

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ChatClient.Builder chatClientBuilder) {

		return args -> {
			// ------------------------------------------------------------
			// PARALLEL WORKFLOW
			// ------------------------------------------------------------

			List<String> parallelResponse = new ParallelizationlWorkflow(chatClientBuilder.build())
					.parallel("""
                分析教研项目“以大语言模型为核，驱动《企业级应用软件设计与开发》课程教学革新”将如何影响各利益相关群体。
                提供具体影响、潜在问题及建议策略。
                要求逻辑清晰、数据支撑、措施可行。
                """,
							List.of(
									"""
                                            学生群体：
                                            - 基础水平差异显著
                                            - 技术接受能力不同
                                            - 学习目标多元化
                                            """,

									"""
                                            教师群体：
                                            - 教学理念存在代际差异
                                            - 技术应用能力参差不齐
                                            - 课程设计自主权需求
                                            """,

									"""
                                            学校管理层：
                                            - 追求教学质量提升
                                            - 面临资源配置压力
                                            - 需要平衡短期与长期目标
                                            """,

									"""
                                            教育监管部门：
                                            - 政策执行与创新空间平衡
                                            - 评估体系科学性要求
                                            - 区域教育公平性考量
                                            """),
							4); // 并行度设置为4

			System.out.println(parallelResponse);

		};
	}
}
