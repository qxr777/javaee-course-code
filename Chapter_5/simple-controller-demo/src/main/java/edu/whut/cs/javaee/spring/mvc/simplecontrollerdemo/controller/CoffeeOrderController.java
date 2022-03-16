package edu.whut.cs.javaee.spring.mvc.simplecontrollerdemo.controller;

import edu.whut.cs.javaee.spring.mvc.simplecontrollerdemo.controller.request.NewOrderRequest;
import edu.whut.cs.javaee.spring.mvc.simplecontrollerdemo.model.Coffee;
import edu.whut.cs.javaee.spring.mvc.simplecontrollerdemo.model.CoffeeOrder;
import edu.whut.cs.javaee.spring.mvc.simplecontrollerdemo.service.CoffeeOrderService;
import edu.whut.cs.javaee.spring.mvc.simplecontrollerdemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {
    @Autowired
    private CoffeeOrderService orderService;
    @Autowired
    private CoffeeService coffeeService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrder) {
        log.info("Receive new Order {}", newOrder);
        Coffee[] coffeeList = coffeeService.getCoffeeByName(newOrder.getItems())
                .toArray(new Coffee[] {});
        return orderService.createOrder(newOrder.getCustomer(), coffeeList);
    }
}
