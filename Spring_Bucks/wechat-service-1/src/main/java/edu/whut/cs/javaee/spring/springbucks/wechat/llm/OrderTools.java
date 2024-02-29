package edu.whut.cs.javaee.spring.springbucks.wechat.llm;

import dev.langchain4j.agent.tool.Tool;
import edu.whut.cs.javaee.spring.springbucks.wechat.integration.CoffeeOrderService;
import edu.whut.cs.javaee.spring.springbucks.wechat.model.CoffeeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderTools {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

//    @Tool
//    public CoffeeOrder getCoffeeOrderDetails(String orderID) {
//        System.out.println("==========================================================================================");
////        System.out.printf("[Tool]: Getting details for booking %s for %s %s...%n", bookingNumber, customerName, customerSurname);
//        System.out.printf("[Tool]: 得到编号为 %s 的订单详情", orderID);
//        System.out.println("==========================================================================================");
//
//        return coffeeOrderService.getOrder(Long.parseLong(orderID));
//    }

}