package edu.whut.cs.javaee.spring.orm.jpademo.repository;

import edu.whut.cs.javaee.spring.orm.jpademo.model.CoffeeOrder;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeOrderRepository extends CrudRepository<CoffeeOrder, Long> {
}
