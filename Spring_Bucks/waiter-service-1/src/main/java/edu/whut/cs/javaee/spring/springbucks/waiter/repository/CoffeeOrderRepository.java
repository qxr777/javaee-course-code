package edu.whut.cs.javaee.spring.springbucks.waiter.repository;

import edu.whut.cs.javaee.spring.springbucks.waiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
