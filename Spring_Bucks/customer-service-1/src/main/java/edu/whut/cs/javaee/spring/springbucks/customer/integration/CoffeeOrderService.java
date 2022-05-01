package edu.whut.cs.javaee.spring.springbucks.customer.integration;

import edu.whut.cs.javaee.spring.springbucks.customer.model.CoffeeOrder;
import edu.whut.cs.javaee.spring.springbucks.customer.model.NewOrderRequest;
import edu.whut.cs.javaee.spring.springbucks.customer.model.OrderStateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "waiter-service", contextId = "coffeeOrder")
public interface CoffeeOrderService {
    @GetMapping("/order/{id}")
    CoffeeOrder getOrder(@PathVariable("id") Long id);

    @PostMapping(path = "/order/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CoffeeOrder create(@RequestBody NewOrderRequest newOrder);

    @PutMapping("/order/{id}")
    CoffeeOrder updateState(@PathVariable("id") Long id,
                            @RequestBody OrderStateRequest orderState);
}
