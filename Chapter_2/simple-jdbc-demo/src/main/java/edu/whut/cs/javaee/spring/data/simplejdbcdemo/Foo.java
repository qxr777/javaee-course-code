package edu.whut.cs.javaee.spring.data.simplejdbcdemo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Foo {
    private Long id;
    private String bar;
}
