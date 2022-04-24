package edu.whut.cs.javaee.spring.cloud.consulwaiterservice.repository;

import edu.whut.cs.javaee.spring.cloud.consulwaiterservice.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
