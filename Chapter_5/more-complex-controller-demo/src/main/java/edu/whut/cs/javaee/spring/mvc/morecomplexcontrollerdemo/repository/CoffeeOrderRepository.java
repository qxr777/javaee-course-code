package edu.whut.cs.javaee.spring.mvc.morecomplexcontrollerdemo.repository;

import edu.whut.cs.javaee.spring.mvc.morecomplexcontrollerdemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
