package edu.whut.cs.javaee.spring.cloud.zipkinwaiterservice.integration;

import edu.whut.cs.javaee.spring.cloud.zipkinwaiterservice.model.CoffeeOrder;
import edu.whut.cs.javaee.spring.cloud.zipkinwaiterservice.repository.CoffeeOrderRepository;
import edu.whut.cs.javaee.spring.cloud.zipkinwaiterservice.service.CoffeeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener {
    @Autowired
    private Customer customer;
    @Autowired
    private CoffeeOrderService orderService;

    @StreamListener(Barista.FINISHED_ORDERS)
    public void listenFinishedOrders(Long id) {
        log.info("We've finished an order [{}].", id);
        CoffeeOrder order = orderService.get(id);
        log.info("Get Order: {}", order);
        Message<Long> message = MessageBuilder.withPayload(id)
                .setHeader("customer", order.getCustomer())
                .build();
        log.info("Notify the customer: {}", order.getCustomer());
        customer.notification().send(message);
    }
}
