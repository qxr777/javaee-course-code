package edu.whut.cs.javaee.spring.cloud.zipkinwaiterservice.controller.request;

import edu.whut.cs.javaee.spring.cloud.zipkinwaiterservice.model.OrderState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderStateRequest {
    private OrderState state;
}
