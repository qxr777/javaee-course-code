package edu.whut.cs.javaee.spring.mvc.exceptiondemo.repository;

import edu.whut.cs.javaee.spring.mvc.exceptiondemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
