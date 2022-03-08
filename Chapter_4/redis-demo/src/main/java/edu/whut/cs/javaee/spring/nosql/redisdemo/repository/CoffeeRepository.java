package edu.whut.cs.javaee.spring.nosql.redisdemo.repository;

import edu.whut.cs.javaee.spring.nosql.redisdemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    Coffee findByName(String name);
}
