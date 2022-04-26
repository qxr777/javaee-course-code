package edu.whut.cs.javaee.spring.cloud.zipkincustomerservice.support;

import edu.whut.cs.javaee.spring.cloud.zipkincustomerservice.model.CoffeeOrder;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class OrderWaitingEvent extends ApplicationEvent {
    private CoffeeOrder order;

    public OrderWaitingEvent(CoffeeOrder order) {
        super(order);
        this.order = order;
    }
}
