package edu.whut.cs.javaee.spring.mvc.complexcontrollerdemo.controller;

import edu.whut.cs.javaee.spring.mvc.complexcontrollerdemo.controller.request.NewOrderRequest;
import edu.whut.cs.javaee.spring.mvc.complexcontrollerdemo.model.Coffee;
import edu.whut.cs.javaee.spring.mvc.complexcontrollerdemo.model.CoffeeOrder;
import edu.whut.cs.javaee.spring.mvc.complexcontrollerdemo.service.CoffeeOrderService;
import edu.whut.cs.javaee.spring.mvc.complexcontrollerdemo.service.CoffeeService;
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
        return orderService.get(id);
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrder) {
        log.info("Receive new Order {}", newOrder);
        Coffee[] coffeeList = coffeeService.getCoffeeByName(newOrder.getItems())
                .toArray(new Coffee[] {});
        return orderService.createOrder(newOrder.getCustomer(), coffeeList);
    }
}
