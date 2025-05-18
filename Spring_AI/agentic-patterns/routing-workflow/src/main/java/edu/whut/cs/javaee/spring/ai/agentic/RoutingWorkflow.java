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

import java.util.Map;

/**
 * Implements the Routing workflow pattern that classifies input and directs it
 * to specialized
 * followup tasks. This workflow enables separation of concerns by routing
 * different types
 * of inputs to specialized prompts and processes optimized for specific
 * categories.
 * 
 * <p>
 * The routing workflow is particularly effective for complex tasks where:
 * <ul>
 * <li>There are distinct categories of input that are better handled
 * separately</li>
 * <li>Classification can be handled accurately by an LLM or traditional
 * classification model</li>
 * <li>Different types of input require different specialized processing or
 * expertise</li>
 * </ul>
 * 
 * <p>
 * Common use cases include:
 * <ul>
 * <li>Customer support systems routing different types of queries (billing,
 * technical, etc.)</li>
 * <li>Content moderation systems routing content to appropriate review
 * processes</li>
 * <li>Query optimization by routing simple/complex questions to different model
 * capabilities</li>
 * </ul>
 * 
 * <p>
 * This implementation allows for dynamic routing based on content
 * classification,
 * with each route having its own specialized prompt optimized for specific
 * types of input.
 * 
 * <p/>
 * Implementation uses the <a href=
 * "https://docs.spring.io/spring-ai/reference/1.0/api/structured-output-converter.html">Spring
 * AI Structure Output</a> to convert the chat client response into a structured
 * {@link RoutingResponse} object.
 * 
 * @author Christian Tzolov
 * @see ChatClient
 * @see <a href=
 *      "https://docs.spring.io/spring-ai/reference/1.0/api/chatclient.html">Spring
 *      AI ChatClient</a>
 * @see <a href=
 *      "https://www.anthropic.com/research/building-effective-agents">Building
 *      Effective Agents</a>
 * @see <a href=
 *      "https://docs.spring.io/spring-ai/reference/1.0/api/structured-output-converter.html">Spring
 *      AI Structure Output</a>
 * 
 */
public class RoutingWorkflow {

    private final ChatClient chatClient;

    public RoutingWorkflow(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * Routes input to a specialized prompt based on content classification. This
     * method
     * first analyzes the input to determine the most appropriate route, then
     * processes
     * the input using the specialized prompt for that route.
     * 
     * <p>
     * The routing process involves:
     * <ol>
     * <li>Content analysis to determine the appropriate category</li>
     * <li>Selection of a specialized prompt optimized for that category</li>
     * <li>Processing the input with the selected prompt</li>
     * </ol>
     *
     * <p>
     * This approach allows for:
     * <ul>
     * <li>Better handling of diverse input types</li>
     * <li>Optimization of prompts for specific categories</li>
     * <li>Improved accuracy through specialized processing</li>
     * </ul>
     *
     * @param input  The input text to be routed and processed
     * @param routes Map of route names to their corresponding specialized prompts
     * @return Processed response from the selected specialized route
     */
    public String route(String input, Map<String, String> routes) {
        Assert.notNull(input, "Input text cannot be null");
        Assert.notEmpty(routes, "Routes map cannot be null or empty");

        // Determine the appropriate route for the input
        String routeKey = determineRoute(input, routes.keySet());

        // Get the selected prompt from the routes map
        String selectedPrompt = routes.get(routeKey);

        if (selectedPrompt == null) {
            throw new IllegalArgumentException("Selected route '" + routeKey + "' not found in routes map");
        }

        // Process the input with the selected prompt
        return chatClient.prompt(selectedPrompt + "\nInput: " + input)
                .user(u -> u.text(selectedPrompt)
                        .param("course_name", Application.COURSE_NAME)
                        .param("input", input))
                .call().content();
    }

    /**
     * Analyzes the input content and determines the most appropriate route based on
     * content classification. The classification process considers key terms,
     * context,
     * and patterns in the input to select the optimal route.
     * 
     * <p>
     * The method uses an LLM to:
     * <ul>
     * <li>Analyze the input content and context</li>
     * <li>Consider the available routing options</li>
     * <li>Provide reasoning for the routing decision</li>
     * <li>Select the most appropriate route</li>
     * </ul>
     *
     * @param input           The input text to analyze for routing
     * @param availableRoutes The set of available routing options
     * @return The selected route key based on content analysis
     */
    @SuppressWarnings("null")
    private String determineRoute(String input, Iterable<String> availableRoutes) {
        System.out.println("\nAvailable routes: " + availableRoutes);

        String selectorPrompt = String.format("""
                分析输入信息，并从以下选项中选择最合适的支持团队： %s
                首先解释您的理由，然后以这种 JSON 格式提供您的选择：                

                \\{
                    “reasoning"： “简要解释为什么要将此票单路由到特定团队。
                                请考虑关键术语、用户意图和紧急程度。"、
                    “selection"： “所选团队名称”
                \\}
                输入: %s""", availableRoutes, input);

        RoutingResponse routingResponse = chatClient.prompt(selectorPrompt).call().entity(RoutingResponse.class);

        System.out.println(String.format("Routing Analysis:%s\nSelected route: %s",
                routingResponse.reasoning(), routingResponse.selection()));

        return routingResponse.selection();
    }
}
