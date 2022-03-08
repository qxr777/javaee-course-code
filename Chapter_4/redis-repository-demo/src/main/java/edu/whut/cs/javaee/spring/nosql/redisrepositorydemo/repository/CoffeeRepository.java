package edu.whut.cs.javaee.spring.nosql.redisrepositorydemo.repository;

import edu.whut.cs.javaee.spring.nosql.redisrepositorydemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
