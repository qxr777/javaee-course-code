package edu.whut.cs.javaee.spring.springbucks.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coffee implements Serializable {
    private Long id;
    private String name;
//    private Money price;
    private Long price;
    private Date createTime;
    private Date updateTime;
}
