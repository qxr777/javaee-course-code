package edu.whut.cs.javaee.spring.springbucks.wechat.llm;

import dev.langchain4j.agent.tool.Tool;
import edu.whut.cs.javaee.spring.springbucks.wechat.integration.CoffeeOrderService;
import edu.whut.cs.javaee.spring.springbucks.wechat.model.CoffeeOrder;
import edu.whut.cs.javaee.spring.springbucks.wechat.model.NewOrderRequest;
import edu.whut.cs.javaee.spring.springbucks.wechat.model.OrderState;
import edu.whut.cs.javaee.spring.springbucks.wechat.model.OrderStateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingTools {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Tool
    public CoffeeOrder getBookingDetails(String bookingNumber, String customerName) {
//    public Booking getBookingDetails(String bookingNumber, String customerName) {
        System.out.println("==========================================================================================");
//        System.out.printf("[Tool]: Getting details for booking %s for %s %s...%n", bookingNumber, customerName, customerSurname);
        System.out.printf("[Tool]: 得到订单详情 %s for %s...%n", bookingNumber, customerName);
        System.out.println("==========================================================================================");

//        return bookingService.getBookingDetails(bookingNumber, customerName);
        return coffeeOrderService.getOrder(Long.parseLong(bookingNumber));
    }

    @Tool
    public CoffeeOrder cancelBooking(String bookingNumber, String customerName) {
        System.out.println("==========================================================================================");
//        System.out.printf("[Tool]: Cancelling booking %s for %s %s...%n", bookingNumber, customerName);
        System.out.printf("[Tool]: 取消订单 %s for %s ...%n", bookingNumber, customerName);
        System.out.println("==========================================================================================");

//        bookingService.cancelBooking(bookingNumber, customerName, customerSurname);
        return coffeeOrderService.updateState(Long.parseLong(bookingNumber), OrderStateRequest.builder().state(OrderState.CANCELLED).build());
    }

    @Tool
    public CoffeeOrder createBooking(String coffeeName, String customerName) {
        System.out.println("==========================================================================================");
//        System.out.printf("[Tool]: Cancelling booking %s for %s %s...%n", bookingNumber, customerName);
        System.out.printf("[Tool]: 创建订单 %s for %s ...%n", coffeeName, customerName);
        System.out.println("==========================================================================================");

//        bookingService.cancelBooking(bookingNumber, customerName, customerSurname);
        List<String> coffeeNames = new ArrayList<>();
        coffeeNames.add(coffeeName);
        return coffeeOrderService.create(NewOrderRequest.builder().customer(customerName).items(coffeeNames).build());
    }
}