package edu.whut.cs.javaee.spring.cloud.rabbitmqbaristaservice.repository;

import edu.whut.cs.javaee.spring.cloud.rabbitmqbaristaservice.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
