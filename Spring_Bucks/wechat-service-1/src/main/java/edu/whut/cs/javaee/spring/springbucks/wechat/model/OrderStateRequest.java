package edu.whut.cs.javaee.spring.springbucks.wechat.model;

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
