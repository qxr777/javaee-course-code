package edu.whut.cs.javaee.spring.springbucks.wechat.llm;

import dev.langchain4j.agent.tool.Tool;
import edu.whut.cs.javaee.spring.springbucks.wechat.integration.CoffeeOrderService;
import edu.whut.cs.javaee.spring.springbucks.wechat.integration.CoffeeService;
import edu.whut.cs.javaee.spring.springbucks.wechat.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingTools {

//    @Autowired
//    private BookingService bookingService;

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Tool
    public CoffeeOrder getBookingDetails(String bookingNumber, String customerName) {
        System.out.println("==========================================================================================");
        System.out.printf("[Tool]: 得到订单详情 %s for %s...%n", bookingNumber, customerName);
        System.out.println("==========================================================================================");

        return coffeeOrderService.getOrder(Long.parseLong(bookingNumber));
    }

    @Tool
    public CoffeeOrder cancelBooking(String bookingNumber, String customerName) {
        System.out.println("==========================================================================================");
        System.out.printf("[Tool]: 取消订单 %s for %s ...%n", bookingNumber, customerName);
        System.out.println("==========================================================================================");

        return coffeeOrderService.updateState(Long.parseLong(bookingNumber), OrderStateRequest.builder().state(OrderState.CANCELLED).build());
    }

    @Tool
    public CoffeeOrder createBooking(String coffeeName, String customerName) {
        System.out.println("==========================================================================================");
        System.out.printf("[Tool]: 创建订单 %s for %s ...%n", coffeeName, customerName);
        System.out.println("==========================================================================================");

        List<String> coffeeNames = new ArrayList<>();
        coffeeNames.add(coffeeName);
        return coffeeOrderService.create(NewOrderRequest.builder().customer(customerName).items(coffeeNames).build());
    }

    /**
     * 获取目前提供的咖啡列表。
     * 该方法不接受任何参数，调用时会从咖啡服务中获取所有可用的咖啡种类，并将其以列表形式返回。
     *
     * @return List<Coffee> 返回一个包含所有可用咖啡的列表。
     */
    @Tool
    public List<Coffee> getAvailableCoffees() {
        // 打印日志信息，标识开始获取可用咖啡列表
        System.out.println("==========================================================================================");
        System.out.println("[Tool]: 获取目前提供的咖啡列表 ...");
        System.out.println("==========================================================================================");

        // 从咖啡服务中获取所有可用的咖啡，并返回这个列表
        return coffeeService.getAll();
    }
}