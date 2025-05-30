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

import java.util.List;

/**
 * Pattern: <b>Orchestrator-workers</b>
 * <p/>
 * In this pattern, a central LLM (the orchestrator) dynamically breaks down
 * complex tasks into subtasks,
 * delegates them to worker LLMs, and uses a synthesizer to combine their
 * results. The orchestrator analyzes
 * the input to determine what subtasks are needed and how they should be
 * executed, while the workers focus
 * on their specific assigned tasks. Finally, the synthesizer integrates the
 * workers' outputs into a cohesive result.
 * <p/>
 * Key components:
 * <ul>
 * <li>Orchestrator: Central LLM that analyzes tasks and determines required
 * subtasks</li>
 * <li>Workers: Specialized LLMs that execute specific subtasks</li>
 * <li>Synthesizer: Component that combines worker outputs into final
 * result</li>
 * </ul>
 * <p/>
 * When to use: This pattern is well-suited for complex tasks where you can't
 * predict the subtasks needed upfront.
 * For example:
 * <ul>
 * <li>Coding tasks where the number of files to change and nature of changes
 * depend on the specific request</li>
 * <li>Search tasks that involve gathering and analyzing information from
 * multiple sources</li>
 * </ul>
 * <p/>
 * While topographically similar to parallelization, the key difference is its
 * flexibility—subtasks aren't
 * pre-defined, but dynamically determined by the orchestrator based on the
 * specific input. This makes it
 * particularly effective for tasks that require adaptive problem-solving and
 * coordination between multiple
 * specialized components.
 * 
 * @author Christian Tzolov
 * @see <a href=
 *      "https://www.anthropic.com/research/building-effective-agents">Building
 *      effective agents</a>
 */
public class OrchestratorWorkers {

	private final ChatClient chatClient;
	private final String orchestratorPrompt;
	private final String workerPrompt;

	public static final String DEFAULT_ORCHESTRATOR_PROMPT = """
			分析这项任务，并将其分解为不超过5步子任务：

			任务: {task}
			
			以已下 JSON 格式返回您的响应：
			\\{
			"analysis"： "解释您对任务的理解，以及哪些变化是有价值的。重点说明每种方法如何服务于任务的不同方面。"、
			"tasks"： [
				\\{
				"step"： "1"、
				"description"： "子任务描述在此"
				\\},
				\\{
				"step"： "2"、
				"description"： "子任务描述在此"
				\\},
				\\{
				"step"： "3"、
				"description"： "子任务描述在此"
				\\}......
			]
			\\}
			""";

	public static final String DEFAULT_WORKER_PROMPT = """
			根据以下内容生成内容：
			任务： {original_task}
			步数： {task_step}
			指导原则： {task_description}
			""";

	/**
	 * Represents a subtask identified by the orchestrator that needs to be executed
	 * by a worker.
	 * 
	 * @param step        The type or category of the task (e.g., "formal",
	 *                    "conversational")
	 * @param description Detailed description of what the worker should accomplish
	 */
	public static record Task(String step, String description) {
	}

	/**
	 * Response from the orchestrator containing task analysis and breakdown into
	 * subtasks.
	 * 
	 * @param analysis Detailed explanation of the task and how different approaches
	 *                 serve its aspects
	 * @param tasks    List of subtasks identified by the orchestrator to be
	 *                 executed by workers
	 */
	public static record OrchestratorResponse(String analysis, List<Task> tasks) {
	}

	/**
	 * Final response containing the orchestrator's analysis and combined worker
	 * outputs.
	 * 
	 * @param analysis        The orchestrator's understanding and breakdown of the
	 *                        original task
	 * @param workerResponses List of responses from workers, each handling a
	 *                        specific subtask
	 */
	public static record FinalResponse(String analysis, List<String> workerResponses) {
	}

	/**
	 * Creates a new OrchestratorWorkers with default prompts.
	 * 
	 * @param chatClient The ChatClient to use for LLM interactions
	 */
	public OrchestratorWorkers(ChatClient chatClient) {
		this(chatClient, DEFAULT_ORCHESTRATOR_PROMPT, DEFAULT_WORKER_PROMPT);
	}

	/**
	 * Creates a new OrchestratorWorkers with custom prompts.
	 * 
	 * @param chatClient         The ChatClient to use for LLM interactions
	 * @param orchestratorPrompt Custom prompt for the orchestrator LLM
	 * @param workerPrompt       Custom prompt for the worker LLMs
	 */
	public OrchestratorWorkers(ChatClient chatClient, String orchestratorPrompt, String workerPrompt) {
		Assert.notNull(chatClient, "ChatClient must not be null");
		Assert.hasText(orchestratorPrompt, "Orchestrator prompt must not be empty");
		Assert.hasText(workerPrompt, "Worker prompt must not be empty");

		this.chatClient = chatClient;
		this.orchestratorPrompt = orchestratorPrompt;
		this.workerPrompt = workerPrompt;
	}

	/**
	 * Processes a task using the orchestrator-workers pattern.
	 * First, the orchestrator analyzes the task and breaks it down into subtasks.
	 * Then, workers execute each subtask in parallel.
	 * Finally, the results are combined into a single response.
	 * 
	 * @param taskDescription Description of the task to be processed
	 * @return WorkerResponse containing the orchestrator's analysis and combined
	 *         worker outputs
	 * @throws IllegalArgumentException if taskDescription is null or empty
	 */
	@SuppressWarnings("null")
	public FinalResponse process(String taskDescription) {
		Assert.hasText(taskDescription, "Task description must not be empty");

		// Step 1: Get orchestrator response
		OrchestratorResponse orchestratorResponse = this.chatClient.prompt()
				.user(u -> u.text(this.orchestratorPrompt)
						.param("task", taskDescription))
				.call()
				.entity(OrchestratorResponse.class);

		System.out.println(String.format("\n=== ORCHESTRATOR OUTPUT ===\nANALYSIS: %s\n\nTASKS: %s\n",
				orchestratorResponse.analysis(), orchestratorResponse.tasks()));

		// Step 2: Process each task
		List<String> workerResponses = orchestratorResponse.tasks().stream().map(task -> this.chatClient.prompt()
				.user(u -> u.text(this.workerPrompt)
						.param("original_task", taskDescription)
						.param("task_step", task.step())
						.param("task_description", task.description()))
				.call()
				.content()).toList();

		System.out.println("\n=== WORKER OUTPUT ===\n" );

		for(int i = 0; i < workerResponses.size(); i++) {
			System.out.println("Worker " + (i + 1) + ": ");
			System.out.println("task_description: " + orchestratorResponse.tasks().get(i));
			System.out.println(workerResponses.get(i));
		}

		return new FinalResponse(orchestratorResponse.analysis(), workerResponses);
	}

}
