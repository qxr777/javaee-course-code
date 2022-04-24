package edu.whut.cs.javaee.spring.cloud.hystrixcustomerservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import edu.whut.cs.javaee.spring.cloud.hystrixcustomerservice.integration.CoffeeOrderService;
import edu.whut.cs.javaee.spring.cloud.hystrixcustomerservice.integration.CoffeeService;
import edu.whut.cs.javaee.spring.cloud.hystrixcustomerservice.model.Coffee;
import edu.whut.cs.javaee.spring.cloud.hystrixcustomerservice.model.CoffeeOrder;
import edu.whut.cs.javaee.spring.cloud.hystrixcustomerservice.model.NewOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/menu")
    public List<Coffee> readMenu() {
        List<Coffee> list = coffeeService.getAll();
        log.info("Read Menu: {} coffee", list.size());
        return list;
    }

    @PostMapping("/order")
    @HystrixCommand(fallbackMethod = "fallbackCreateOrder")
    public CoffeeOrder createOrder() {
        NewOrderRequest orderRequest = NewOrderRequest.builder()
                .customer("Li Lei")
                .items(Arrays.asList("capuccino"))
                .build();
        CoffeeOrder order = coffeeOrderService.create(orderRequest);
        log.info("Order ID: {}", order != null ? order.getId() : "-");
        return order;
    }

    public CoffeeOrder fallbackCreateOrder() {
        log.warn("Fallback to NULL order.");
        return null;
    }
}
