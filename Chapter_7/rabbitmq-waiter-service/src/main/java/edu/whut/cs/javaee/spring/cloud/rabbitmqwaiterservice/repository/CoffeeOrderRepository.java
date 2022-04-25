package edu.whut.cs.javaee.spring.cloud.rabbitmqwaiterservice.repository;

import edu.whut.cs.javaee.spring.cloud.rabbitmqwaiterservice.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
