package edu.whut.cs.javaee.spring.cloud.eurekawaiterservice.controller;

import edu.whut.cs.javaee.spring.cloud.eurekawaiterservice.controller.request.NewOrderRequest;
import edu.whut.cs.javaee.spring.cloud.eurekawaiterservice.model.Coffee;
import edu.whut.cs.javaee.spring.cloud.eurekawaiterservice.model.CoffeeOrder;
import edu.whut.cs.javaee.spring.cloud.eurekawaiterservice.service.CoffeeOrderService;
import edu.whut.cs.javaee.spring.cloud.eurekawaiterservice.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {
    @Autowired
    private CoffeeOrderService orderService;
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/{id}")
    public CoffeeOrder getOrder(@PathVariable("id") Long id) {
        CoffeeOrder order = orderService.get(id);
        log.info("Get Order: {}", order);
        return order;
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrder) {
        log.info("Receive new Order {}", newOrder);
        Coffee[] coffeeList = coffeeService.getCoffeeByName(newOrder.getItems())
                .toArray(new Coffee[] {});
        return orderService.createOrder(newOrder.getCustomer(), coffeeList);
    }
}
