package edu.whut.cs.javaee.spring.cloud.eurekawaiterservice.repository;

import edu.whut.cs.javaee.spring.cloud.eurekawaiterservice.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    List<Coffee> findByNameInOrderById(List<String> list);
    Coffee findByName(String name);
}
