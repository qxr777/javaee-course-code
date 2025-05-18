
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
import java.util.Map;

// ------------------------------------------------------------
// ROUTER WORKFLOW
// ------------------------------------------------------------
@SpringBootApplication
public class Application {

	public final static String COURSE_NAME = "企业级应用软件设计与开发";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ChatClient.Builder chatClientBuilder) {
		
		return args -> {
			Map<String, String> supportRoutes = Map.of("designer",
					"""						
							在{course_name}课程准备阶段，当明确课程产品定位后，需要进行从课程到单元再到具体教学的系统设计，包含各层级目标、评价设计以及教学评一致性检查等工作时，课前课程设计 Agent 将发挥关键作用。

							你是一名专注于{course_name}的资深课程设计师，已知课程产品定位，需依次完成课程设计、单元设计和教学设计，确保各层级目标明确、评价方式有效、教学评保持一致，同时设计核心教学案例与变式练习题，设计过程要紧密围绕 {course_name}教育特点及学生认知水平展开 。
							
							Input: {input}
						  """,

					"assistant",
					"""						
							在{course_name}课中，当学生对编程概念理解困难、编写代码遇到逻辑错误或不知如何推进项目时，课中学习引导 Agent 能及时给予帮助。
							
							你是学生在 {course_name}课上的专属学习伙伴，要使用通俗易懂、生动有趣的语言，结合学生已学知识和当前编程进度，引导学生理解编程概念、解决代码逻辑问题并推进编程项目，避免使用专业晦涩术语，时刻保持耐心和热情。

							Input: {input}
						""",

					"supervisor",
					"""
							在 {course_name}课堂中，实时监测学生学习状态、交流内容及编程操作，维护课堂秩序、保障学生学习顺利进行。
							
							你是{course_name}课堂的智能监督管理助手，要时刻留意学生的编程操作、交流对话。一旦发现学生创作受阻、出现不当言论或指出 AI 信息错误，需立刻按规定处理。注意依据学生在编程学习中的行为模式和交流内容，精准判断异常情况，维护良好的课堂学习氛围 。

							Input: {input}
						""",

					"reporter",
					"""
							在{course_name}课程结束后，当教师需要全面了解学生学习情况、为学生提供针对性学习建议，以及家长期望知晓孩子学习成果与不足时，课后学习报告 Agent 可依据学生课中学习数据生成详细学习报告。
							
							你是一位专业的课程学习报告生成专家，根据提供的学生在{course_name}课中的课堂参与、作业完成、代码实践等数据，从知识掌握、问题解决能力、学习态度方面进行评估，按照总分总结构，先总结学生整体学习表现，再详细分析优势与不足并举例说明，最后给出个性化学习建议，生成一份逻辑清晰、内容详实的学习报告。

							Input: {input}
						""");

			List<String> tickets = List.of(
					"""
					   课程结束了，请分析一下这次上课的效果如何？
					""",

					"""
					   我要翘课，不参加考核。
					""",

					"""
						课程结束后，学生会有哪些收获？
					""");

			var routerWorkflow = new RoutingWorkflow(chatClientBuilder.build());

			int i = 1;
			for (String ticket : tickets) {
				System.out.println("\nTicket " + i++);
				System.out.println("------------------------------------------------------------");
				System.out.println(ticket);
				System.out.println("------------------------------------------------------------");
				System.out.println(routerWorkflow.route(ticket, supportRoutes));
			}

		};
	}
}
