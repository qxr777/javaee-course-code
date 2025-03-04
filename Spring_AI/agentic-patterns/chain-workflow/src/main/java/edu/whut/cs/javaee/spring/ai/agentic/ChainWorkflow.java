package edu.whut.cs.javaee.spring.ai.agentic;

import org.springframework.ai.chat.client.ChatClient;

/**
 * Implements the Prompt Chaining workflow pattern for decomposing complex tasks
 * into a sequence
 * of LLM calls where each step processes the output of the previous one.
 * 
 * <p>
 * This implementation demonstrates a four-step workflow for processing
 * numerical data in text:
 * <ol>
 * <li>Extract numerical values and metrics</li>
 * <li>Standardize to percentage format</li>
 * <li>Sort in descending order</li>
 * <li>Format as markdown table</li>
 * </ol>
 * 
 * <p/>
 * When to use this workflow: This workflow is ideal for situations where the
 * task can be easily and cleanly decomposed into fixed subtasks. The main goal
 * is to trade off latency for higher accuracy, by making each LLM call an
 * easier task.
 * 
 * @author Christian Tzolov
 * @see ChatClient
 * @see <a href="https://docs.spring.io/spring-ai/reference/1.0/api/chatclient.html">Spring AI ChatClient</a>
 * @see <a href=
 *      "https://www.anthropic.com/research/building-effective-agents">Building
 *      Effective Agents</a>
 */
public class ChainWorkflow {

	/**
	 * Array of system prompts that define the transformation steps in the chain.
	 * Each prompt acts as a gate that validates and transforms the output before
	 * proceeding to the next step.
	 */
	private static final String[] DEFAULT_SYSTEM_PROMPTS = {

			// Step 1
			"""
					只从文本中提取数值及其相关度量。
					格式为 “value: metric”，另起一行。
					格式示例
					92：客户满意度
					45%：收入增长""",
			// Step 2
			"""
					尽可能将所有数值转换为百分比。
					如果不是百分比或点数，则转换为小数（例如，92 点 -> 92%）。
					每行保留一个数字。
					格式示例：
					92%：客户满意度
					45%：收入增长""",
			// Step 3
			"""
					按数值降序排列所有行。
					保持每行的格式为 “value: metric”。
					例如
					92%：客户满意度
					87%：员工满意度""",
			// Step 4
			"""
					将排序后的数据格式化为带列的HTML表，不要带```html
					"""
	};

	private final ChatClient chatClient;

	private final String[] systemPrompts;

	/**
	 * Constructs a new instance of the Prompt Chaining workflow with the specified
	 * chat client and default system prompts.
	 * 
	 * @param chatClient the Spring AI chat client used to make LLM calls
	 */
	public ChainWorkflow(ChatClient chatClient) {
		this(chatClient, DEFAULT_SYSTEM_PROMPTS);
	}

	/**
	 * Constructs a new instance of the Prompt Chaining workflow with the specified
	 * chat client and system prompts.
	 * 
	 * @param chatClient    the Spring AI chat client used to make LLM calls
	 * @param systemPrompts the system prompts that define the transformation steps
	 *                      in the chain
	 */
	public ChainWorkflow(ChatClient chatClient, String[] systemPrompts) {
		this.chatClient = chatClient;
		this.systemPrompts = systemPrompts;
	}

	/**
	 * Executes the prompt chaining workflow by processing the input text through
	 * a series of LLM calls, where each call's output becomes the input for the
	 * next step.
	 * 
	 * <p>
	 * The method prints the intermediate results after each step to show the
	 * progression of transformations through the chain.
	 *
	 * @param chatClient the Spring AI chat client used to make LLM calls
	 * @param userInput     the input text containing numerical data to be processed
	 * @return the final output after all steps have been executed
	 */
	public String chain(String userInput) {

		int step = 0;
		String response = userInput;
		System.out.println(String.format("\nSTEP %s:\n %s", step++, response));

		for (String prompt : systemPrompts) {

			// 1. Compose the input using the response from the previous step.
			String input = String.format("{%s}\n {%s}", prompt, response);

			// 2. Call the chat client with the new input and get the new response.
			response = chatClient.prompt(input).call().content();

			System.out.println(String.format("\nSTEP %s:\n %s", step++, response));
		}

		return response;
	}
}
