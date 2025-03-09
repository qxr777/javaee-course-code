package edu.whut.cs.javaee.spring.springbucks.chat.services;

import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;

public class LoggingAdvisor extends SimpleLoggerAdvisor {

	@Override
	public AdvisedResponse aroundCall(AdvisedRequest request, CallAroundAdvisorChain chain) {
		System.out.println("Request: " + request);
		return chain.nextAroundCall(request);
	}

    @Override
    public int getOrder() {
        return 0;
    }
}
