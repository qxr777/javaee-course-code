package edu.whut.cs.javaee.spring.cloud.gitconfigwaiterservice.repository;

import edu.whut.cs.javaee.spring.cloud.gitconfigwaiterservice.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
