package edu.whut.cs.javaee.spring.springbucks.mcp.model;

import edu.whut.cs.javaee.spring.springbucks.mcp.model.OrderState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class OrderStateRequest {
    private OrderState state;
}
