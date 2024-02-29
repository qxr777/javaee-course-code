package edu.whut.cs.javaee.spring.springbucks.wechat.llm;

import dev.langchain4j.service.SystemMessage;

public interface CustomerSupportAgent {

    @SystemMessage({
//            "You are a customer support agent of a car rental company named 'Miles of Smiles'.",
//            "Before providing information about booking or cancelling booking, you MUST always check:",
//            "booking number, customer name and surname.",
//            "Today is {{current_date}}."
            "你是一家名为 \"SpringBucks \"的咖啡连锁店的客户支持代理。",
//            "不要谈论与本店业务无关的任何话题。",
            "允许你依据SpringBucks价格表，告知客户咖啡的价格。",
            "在提供有关预订或取消预订的信息之前，您必须始终进行检查：",
            "订单编号",
            "今天是{{current_date}}"
    })
    String chat(String userMessage);
}