package edu.whut.cs.javaee.spring.nosql.cachedemo.repository;

import edu.whut.cs.javaee.spring.nosql.cachedemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
