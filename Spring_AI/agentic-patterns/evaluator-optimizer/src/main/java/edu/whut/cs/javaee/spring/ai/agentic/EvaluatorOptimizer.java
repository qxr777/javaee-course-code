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
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow: <b>Evaluator-optimizer</b>
 * <p/>
 * Implements the Evaluator-Optimizer workflow pattern for Large Language Model
 * (LLM) interactions. This workflow orchestrates a dual-LLM process where one
 * model
 * generates responses while another provides evaluation and feedback in an
 * iterative loop,
 * similar to a human writer's iterative refinement process.
 *
 * <p>
 * The workflow consists of two main components:
 * <ul>
 * <li>A generator LLM that produces initial responses and refines them based on
 * feedback</li>
 * <li>An evaluator LLM that analyzes responses and provides detailed feedback
 * for improvement</li>
 * </ul>
 *
 * <b>Usage Criteria</b>
 * This workflow is particularly effective in scenarios that meet the following
 * conditions:
 * <ul>
 * <li>Clear evaluation criteria exist for assessing response quality</li>
 * <li>Iterative refinement provides measurable value to the output</li>
 * <li>The task benefits from multiple rounds of critique and improvement</li>
 * </ul>
 *
 * <b>Fitness Indicators</b>
 * Two key indicators suggest this workflow is appropriate:
 * <ul>
 * <li>LLM responses can be demonstrably improved when feedback is
 * articulated</li>
 * <li>The evaluator LLM can provide substantive and actionable feedback</li>
 * </ul>
 *
 * <b>Example Applications</b>
 * <ul>
 * <li>Literary translation requiring capture of subtle nuances through
 * iterative refinement</li>
 * <li>Complex search tasks needing multiple rounds of searching and
 * analysis</li>
 * <li>Code generation where quality can be improved through systematic
 * review</li>
 * <li>Content creation requiring multiple drafts and specific improvements</li>
 * </ul>
 * 
 * @author Christian Tzolov
 * @see <a href=
 *      "https://www.anthropic.com/research/building-effective-agents">Building
 *      effective agents</a>
 */
@SuppressWarnings("null")
public class EvaluatorOptimizer {

	public static final String DEFAULT_GENERATOR_PROMPT = """
			您的目标是根据输入完成任务。如果有前人的反馈
				反馈，您应加以反思，以改进您的解决方案。
	   
				关键：您的回复必须是单行有效的 JSON 格式，除了明确用 \\n 转义的字符串外，不得有任何换行符。
				以下是应遵循的准确格式，包括所有引号和大括号：
	   
				{"thoughts": "Brief description here", "response": "public class Example {\\n // Code here\\n}"}
	   
				响应字段的规则：
				1. 所有换行符必须使用 \\n
				2. 所有引号必须使用\\"
				3. 所有反斜线必须加倍： \\
				4. 没有实际的换行或格式化 - 所有内容都在一行中
				5. 无制表符或特殊字符
				6. Java 代码必须完整并正确转义
	   
				格式正确的回复示例：
				{"thoughts": "Implementing counter", "response": "public class Counter {\\n private int count;\\n public Counter() {\\n count = 0;\\n }\\n public void increment() {\\n count++;\\n }\\n}"}
 
			    请完全遵循此格式 - 您的响应必须是单行有效 JSON 格式。
			""";

	public static final String DEFAULT_EVALUATOR_PROMPT = """
			评估代码实现的正确性、时间复杂性和最佳实践。
			确保代码有正确的 javadoc 文档。
			在单行中以完全相同的 JSON 格式进行响应：

			{"evaluation": "PASS, NEEDS_IMPROVEMENT, or FAIL", "feedback": "Your feedback here"}

			评价字段必须是 "PASS"、"NEEDS_IMPROVEMENT"或 "FAIL"之一
			只有在符合所有标准且无需改进时，才使用 "PASS"。
			""";

	/**
	 * Represents a solution generation step. Contains the model's thoughts and the
	 * proposed solution.
	 * 
	 * @param thoughts The model's understanding of the task and feedback
	 * @param response The model's proposed solution
	 */
	public static record Generation(String thoughts, String response) {
	}

	/**
	 * Represents an evaluation response. Contains the evaluation result and
	 * detailed feedback.
	 * 
	 * @param evaluation The evaluation result (PASS, NEEDS_IMPROVEMENT, or FAIL)
	 * @param feedback   Detailed feedback for improvement
	 */
	public static record EvaluationResponse(Evaluation evaluation, String feedback) {

		public enum Evaluation {
			PASS, NEEDS_IMPROVEMENT, FAIL
		}
	}

	/**
	 * Represents the final refined response. Contains the final solution and the
	 * chain of thought showing the evolution of the solution.
	 * 
	 * @param solution       The final solution
	 * @param chainOfThought The chain of thought showing the evolution of the
	 *                       solution
	 */
	public static record RefinedResponse(String solution, List<Generation> chainOfThought) {
	}

	private final ChatClient chatClient;

	private final String generatorPrompt;

	private final String evaluatorPrompt;

	public EvaluatorOptimizer(ChatClient chatClient) {
		this(chatClient, DEFAULT_GENERATOR_PROMPT, DEFAULT_EVALUATOR_PROMPT);
	}

	public EvaluatorOptimizer(ChatClient chatClient, String generatorPrompt, String evaluatorPrompt) {
		Assert.notNull(chatClient, "ChatClient must not be null");
		Assert.hasText(generatorPrompt, "Generator prompt must not be empty");
		Assert.hasText(evaluatorPrompt, "Evaluator prompt must not be empty");

		this.chatClient = chatClient;
		this.generatorPrompt = generatorPrompt;
		this.evaluatorPrompt = evaluatorPrompt;
	}

	/**
	 * Initiates the evaluator-optimizer workflow for a given task. This method
	 * orchestrates the iterative process of generation and evaluation until a
	 * satisfactory solution is reached.
	 * 
	 * <p>
	 * The workflow follows these steps:
	 * </p>
	 * <ol>
	 * <li>Generate an initial solution</li>
	 * <li>Evaluate the solution against quality criteria</li>
	 * <li>If evaluation passes, return the solution</li>
	 * <li>If evaluation indicates need for improvement, incorporate feedback and
	 * generate new solution</li>
	 * <li>Repeat steps 2-4 until a satisfactory solution is achieved</li>
	 * </ol>
	 * 
	 * @param task The task or problem to be solved through iterative refinement
	 * @return A RefinedResponse containing the final solution and the chain of
	 *         thought
	 *         showing the evolution of the solution
	 */
	public RefinedResponse loop(String task) {
		List<String> memory = new ArrayList<>();
		List<Generation> chainOfThought = new ArrayList<>();

		return loop(task, "", memory, chainOfThought);
	}

	/**
	 * Internal recursive implementation of the evaluator-optimizer loop. This
	 * method
	 * maintains the state of previous attempts and feedback while recursively
	 * refining
	 * the solution until it meets the evaluation criteria.
	 * 
	 * @param task           The original task to be solved
	 * @param context        Accumulated context including previous attempts and
	 *                       feedback
	 * @param memory         List of previous solution attempts for reference
	 * @param chainOfThought List tracking the evolution of solutions and reasoning
	 * @return A RefinedResponse containing the final solution and complete solution
	 *         history
	 */
	private RefinedResponse loop(String task, String context, List<String> memory,
			List<Generation> chainOfThought) {

		Generation generation = generate(task, context);
		memory.add(generation.response());
		chainOfThought.add(generation);

		EvaluationResponse evaluationResponse = evalute(generation.response(), task);

		if (evaluationResponse.evaluation().equals(EvaluationResponse.Evaluation.PASS)) {
			// Solution is accepted!
			return new RefinedResponse(generation.response(), chainOfThought);
		}

		// Accumulated new context including the last and the previous attempts and
		// feedbacks.
		StringBuilder newContext = new StringBuilder();
		newContext.append("Previous attempts:");
		for (String m : memory) {
			newContext.append("\n- ").append(m);
		}
		newContext.append("\nFeedback: ").append(evaluationResponse.feedback());

		return loop(task, newContext.toString(), memory, chainOfThought);
	}

	/**
	 * Generates or refines a solution based on the given task and feedback context.
	 * This method represents the generator component of the workflow, producing
	 * responses that can be iteratively improved through evaluation feedback.
	 * 
	 * @param task    The primary task or problem to be solved
	 * @param context Previous attempts and feedback for iterative improvement
	 * @return A Generation containing the model's thoughts and proposed solution
	 */
	private Generation generate(String task, String context) {
		Generation generationResponse = chatClient.prompt()
				.user(u -> u.text("{prompt}\n{context}\nTask: {task}")
						.param("prompt", this.generatorPrompt)
						.param("context", context)
						.param("task", task))
				.call()
				.entity(Generation.class);

		System.out.println(String.format("\n=== GENERATOR OUTPUT ===\nTHOUGHTS: %s\n\nRESPONSE:\n %s\n",
				generationResponse.thoughts(), generationResponse.response()));
		return generationResponse;
	}

	/**
	 * Evaluates if a solution meets the specified requirements and quality
	 * criteria.
	 * This method represents the evaluator component of the workflow, analyzing
	 * solutions
	 * and providing detailed feedback for further refinement until the desired
	 * quality
	 * level is reached.
	 * 
	 * @param content The solution content to be evaluated
	 * @param task    The original task against which to evaluate the solution
	 * @return An EvaluationResponse containing the evaluation result
	 *         (PASS/NEEDS_IMPROVEMENT/FAIL)
	 *         and detailed feedback for improvement
	 */
	private EvaluationResponse evalute(String content, String task) {

		EvaluationResponse evaluationResponse = chatClient.prompt()
				.user(u -> u.text("{prompt}\nOriginal task: {task}\nContent to evaluate: {content}")
						.param("prompt", this.evaluatorPrompt)
						.param("task", task)
						.param("content", content))
				.call()
				.entity(EvaluationResponse.class);

		System.out.println(String.format("\n=== EVALUATOR OUTPUT ===\nEVALUATION: %s\n\nFEEDBACK: %s\n",
				evaluationResponse.evaluation(), evaluationResponse.feedback()));
		return evaluationResponse;
	}

}
