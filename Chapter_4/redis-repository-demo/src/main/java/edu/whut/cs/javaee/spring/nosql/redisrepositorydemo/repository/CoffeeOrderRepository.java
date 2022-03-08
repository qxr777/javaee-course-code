package edu.whut.cs.javaee.spring.nosql.redisrepositorydemo.repository;

import edu.whut.cs.javaee.spring.nosql.redisrepositorydemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
