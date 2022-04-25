package edu.whut.cs.javaee.spring.cloud.eurekawaiterservice.repository;

import edu.whut.cs.javaee.spring.cloud.eurekawaiterservice.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
