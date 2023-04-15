package edu.whut.cs.javaee.spring.webflux.webfluxdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table("t_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeOrder implements Serializable {
    @Id
    private Long id;
    private String customer;
    private OrderState state;
    private List<Coffee> items;
    private Date createTime;
    private Date updateTime;
}
