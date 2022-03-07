package edu.whut.cs.javaee.spring.nosql.cachedemo.repository;

import edu.whut.cs.javaee.spring.nosql.cachedemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
