package edu.whut.cs.javaee.spring.mvc.simplecontrollerdemo.repository;

import edu.whut.cs.javaee.spring.mvc.simplecontrollerdemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
