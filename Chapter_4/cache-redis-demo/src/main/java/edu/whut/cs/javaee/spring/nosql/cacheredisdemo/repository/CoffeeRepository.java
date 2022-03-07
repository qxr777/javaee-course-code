package edu.whut.cs.javaee.spring.nosql.cacheredisdemo.repository;

import edu.whut.cs.javaee.spring.nosql.cacheredisdemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
