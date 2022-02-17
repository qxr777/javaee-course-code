package edu.whut.cs.javaee.spring.springbucks.repository;

import edu.whut.cs.javaee.spring.springbucks.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
