package edu.whut.cs.javaee.spring.orm.jpacomplexdemo.repository;


import edu.whut.cs.javaee.spring.orm.jpacomplexdemo.model.CoffeeOrder;

import java.util.List;

public interface CoffeeOrderRepository extends BaseRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findByCustomerOrderById(String customer);
    List<CoffeeOrder> findByItems_Name(String name);
}
