package edu.whut.cs.javaee.spring.springbucks.customer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import edu.whut.cs.javaee.spring.springbucks.customer.integration.CoffeeOrderService;
import edu.whut.cs.javaee.spring.springbucks.customer.integration.CoffeeService;
import edu.whut.cs.javaee.spring.springbucks.customer.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {
    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CoffeeOrderService coffeeOrderService;
    @Value("${customer.name}")
    private String customer;

    @GetMapping("/menu")
    public List<Coffee> readMenu() {
        List<Coffee> list = coffeeService.getAll();
        log.info("Read Menu: {} coffee", list.size());
        return list;
    }

    @PostMapping("/order")
    @HystrixCommand(fallbackMethod = "fallbackCreateOrder")
    public CoffeeOrder createAndPayOrder() {
        NewOrderRequest orderRequest = NewOrderRequest.builder()
                .customer(customer)
                .items(Arrays.asList("capuccino"))
                .build();
        CoffeeOrder order = coffeeOrderService.create(orderRequest);
        log.info("Create order: {}", order != null ? order.getId() : "-");
        order = coffeeOrderService.updateState(order.getId(),
                OrderStateRequest.builder().state(OrderState.PAID).build());
        log.info("Order is PAID: {}", order);
        return order;
    }

    public CoffeeOrder fallbackCreateOrder() {
        log.warn("Fallback to NULL order.");
        return null;
    }

}
