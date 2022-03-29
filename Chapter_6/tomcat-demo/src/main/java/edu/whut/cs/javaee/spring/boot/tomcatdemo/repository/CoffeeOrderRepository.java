package edu.whut.cs.javaee.spring.boot.tomcatdemo.repository;

import edu.whut.cs.javaee.spring.boot.tomcatdemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
