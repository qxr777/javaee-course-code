package edu.whut.cs.javaee.spring.mvc.interceptordemo.repository;

import edu.whut.cs.javaee.spring.mvc.interceptordemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
