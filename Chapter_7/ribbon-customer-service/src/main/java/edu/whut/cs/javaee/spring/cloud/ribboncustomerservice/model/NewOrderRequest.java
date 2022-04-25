package edu.whut.cs.javaee.spring.cloud.ribboncustomerservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class NewOrderRequest {
    private String customer;
    private List<String> items;
}
