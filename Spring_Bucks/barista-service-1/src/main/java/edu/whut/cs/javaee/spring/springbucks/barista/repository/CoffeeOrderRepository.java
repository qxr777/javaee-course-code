package edu.whut.cs.javaee.spring.springbucks.barista.repository;

import edu.whut.cs.javaee.spring.springbucks.barista.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
