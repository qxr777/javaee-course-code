package edu.whut.cs.javaee.spring.nosql.cacheredisdemo.repository;

import edu.whut.cs.javaee.spring.nosql.cacheredisdemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
