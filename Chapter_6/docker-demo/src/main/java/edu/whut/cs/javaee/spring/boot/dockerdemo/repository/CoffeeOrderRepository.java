package edu.whut.cs.javaee.spring.boot.dockerdemo.repository;

import edu.whut.cs.javaee.spring.boot.dockerdemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
