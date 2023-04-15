package edu.whut.cs.javaee.spring.webflux.webfluxdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table("t_coffee")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coffee implements Serializable {
    @Id
    private Long id;
    private String name;
    private Integer price;
    private Date createTime;
    private Date updateTime;
}
