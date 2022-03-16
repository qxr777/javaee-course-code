package edu.whut.cs.javaee.spring.mvc.complexcontrollerdemo.repository;

import edu.whut.cs.javaee.spring.mvc.complexcontrollerdemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
