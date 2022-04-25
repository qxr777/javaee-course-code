package edu.whut.cs.javaee.spring.cloud.rabbitmqwaiterservice.controller.request;

import edu.whut.cs.javaee.spring.cloud.rabbitmqwaiterservice.model.OrderState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderStateRequest {
    private OrderState state;
}
