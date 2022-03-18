package edu.whut.cs.javaee.spring.mvc.controllercachedemo.repository;

import edu.whut.cs.javaee.spring.mvc.controllercachedemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
