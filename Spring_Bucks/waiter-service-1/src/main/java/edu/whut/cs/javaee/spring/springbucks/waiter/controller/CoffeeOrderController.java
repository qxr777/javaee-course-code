package edu.whut.cs.javaee.spring.springbucks.waiter.controller;

import edu.whut.cs.javaee.spring.springbucks.waiter.controller.request.NewOrderRequest;
import edu.whut.cs.javaee.spring.springbucks.waiter.controller.request.OrderStateRequest;
import edu.whut.cs.javaee.spring.springbucks.waiter.model.Coffee;
import edu.whut.cs.javaee.spring.springbucks.waiter.model.CoffeeOrder;
import edu.whut.cs.javaee.spring.springbucks.waiter.service.CoffeeOrderService;
import edu.whut.cs.javaee.spring.springbucks.waiter.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path = "/", params = "!name")
    public List<CoffeeOrder> getAll() {
        return orderService.getAllOrder();
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

    @PutMapping("/{id}")
    public CoffeeOrder updateState(@PathVariable("id") Long id,
                                   @RequestBody OrderStateRequest orderState) {
        log.info("Update order state: {}", orderState);
        CoffeeOrder order = orderService.get(id);
        orderService.updateState(order, orderState.getState());
        return order;
    }
}
