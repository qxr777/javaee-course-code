package edu.whut.cs.javaee.spring.orm.jpademo.repository;

import edu.whut.cs.javaee.spring.orm.jpademo.model.Coffee;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
}
