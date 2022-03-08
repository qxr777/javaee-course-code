package edu.whut.cs.javaee.spring.nosql.redisdemo.repository;

import edu.whut.cs.javaee.spring.nosql.redisdemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
