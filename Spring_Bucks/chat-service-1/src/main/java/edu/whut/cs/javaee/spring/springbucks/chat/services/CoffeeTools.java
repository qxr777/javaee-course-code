package edu.whut.cs.javaee.spring.springbucks.chat.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.whut.cs.javaee.spring.springbucks.chat.integration.CoffeeOrderService;
//import edu.whut.cs.javaee.spring.springbucks.chat.integration.CoffeeService;
import edu.whut.cs.javaee.spring.springbucks.chat.model.CoffeeOrder;
import edu.whut.cs.javaee.spring.springbucks.chat.model.Coffee;
import edu.whut.cs.javaee.spring.springbucks.chat.model.NewOrderRequest;
import edu.whut.cs.javaee.spring.springbucks.chat.model.OrderState;
import edu.whut.cs.javaee.spring.springbucks.chat.model.OrderStateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.NestedExceptionUtils;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class CoffeeTools {

    private static final Logger logger = LoggerFactory.getLogger(CoffeeTools.class);

//    @Autowired
//    private CoffeeService coffeeService;

    private final CoffeeOrderService coffeeOrderService;

    public CoffeeTools(CoffeeOrderService coffeeOrderService) {
        this.coffeeOrderService = coffeeOrderService;
    }

    public record BookingDetailsRequest(String bookingNumber, String name) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record BookingDetails(String bookingNumber, String name, String items, Date date, OrderState orderState,
                                 Integer discount, Long total, String waiter, String barista) {
    }

    public record BookingCoffeeRequest(String customer, List<String> items) {
    }

    @Tool(description = "获取订单详细信息")
    public CoffeeTools.BookingDetails getOrderDetails(String bookingNumber, String customerName) {
            System.out.println("==========================================================================================");
            System.out.printf("[Tool]: 得到订单详情 %s for %s...%n", bookingNumber, customerName);
            System.out.println("==========================================================================================");
            CoffeeOrder coffeeOrder = coffeeOrderService.getOrder(Long.parseLong(bookingNumber));
        if (coffeeOrder != null) {
            // 从Coffee对象中提取名称并拼接为字符串
            String items = coffeeOrder.getItems().stream()
                    .map(Coffee::getName) // 假设Coffee类中有getName()方法
                    .collect(Collectors.joining(", "));

            return new CoffeeTools.BookingDetails(
                    coffeeOrder.getId() + "",
                    coffeeOrder.getCustomer(),
                    items,
                    coffeeOrder.getCreateTime(),
                    coffeeOrder.getState(),
                    coffeeOrder.getDiscount(),
                    coffeeOrder.getTotal(),
                    coffeeOrder.getWaiter(),
                    coffeeOrder.getBarista()
            );
        } else {
            throw new RuntimeException("Coffee order is null");
        }
    }

    @Tool(description = "根据客人名字和咖啡名，生成新订单")
    public CoffeeOrder orderingCoffee(BookingCoffeeRequest request) {
            System.out.println("==========================================================================================");
            System.out.printf("[Tool]: 创建订单 %s for %s ...%n", request.customer, request.items);
            System.out.println("==========================================================================================");
            NewOrderRequest newOrderRequest = NewOrderRequest.builder().customer(request.customer).items(request.items).build();
            return coffeeOrderService.create(newOrderRequest);
    }

    @Tool(description = "根据客人名字和订单编号，取消订单")
    public CoffeeOrder cancelOrder(String bookingNumber, String customerName) {
        System.out.println("==========================================================================================");
        System.out.printf("[Tool]: 取消订单 %s for %s ...%n", bookingNumber, customerName);
        System.out.println("==========================================================================================");
        return coffeeOrderService.updateState(Long.parseLong(bookingNumber), OrderStateRequest.builder().state(OrderState.CANCELLED).build());
    }
}
