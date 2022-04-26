package edu.whut.cs.javaee.spring.cloud.zipkinwaiterservice.repository;

import edu.whut.cs.javaee.spring.cloud.zipkinwaiterservice.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
