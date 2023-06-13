package edu.whut.cs.javaee.spring.springbucks.customer.integration;

import edu.whut.cs.javaee.spring.springbucks.customer.model.CoffeeOrder;
import edu.whut.cs.javaee.spring.springbucks.customer.model.OrderState;
import edu.whut.cs.javaee.spring.springbucks.customer.model.OrderStateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {
    @Autowired
    private CoffeeOrderService orderService;
    @Value("${customer.name}")
    private String customer;

    @StreamListener(Waiter.NOTIFY_ORDERS)
    public void takeOrder(@Payload Long id) {
        CoffeeOrder order = orderService.getOrder(id);
        if (OrderState.BREWED == order.getState()) {
            log.info("Order {} is READY, I'll take it.", id);
            orderService.updateState(id,
                    OrderStateRequest.builder().state(OrderState.TAKEN).build());
        } else {
            log.warn("Order {} is NOT READY. Why do you notify me?", id);
        }
    }
}
