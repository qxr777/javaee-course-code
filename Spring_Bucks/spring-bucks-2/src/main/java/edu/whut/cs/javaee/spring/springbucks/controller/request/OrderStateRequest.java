package edu.whut.cs.javaee.spring.springbucks.controller.request;


import edu.whut.cs.javaee.spring.springbucks.model.OrderState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderStateRequest {
    private OrderState state;
}
