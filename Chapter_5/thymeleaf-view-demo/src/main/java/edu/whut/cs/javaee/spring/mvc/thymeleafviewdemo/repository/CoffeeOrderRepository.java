package edu.whut.cs.javaee.spring.mvc.thymeleafviewdemo.repository;

import edu.whut.cs.javaee.spring.mvc.thymeleafviewdemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
