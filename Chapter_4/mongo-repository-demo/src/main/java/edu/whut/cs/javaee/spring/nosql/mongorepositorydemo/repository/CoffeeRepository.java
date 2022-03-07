package edu.whut.cs.javaee.spring.nosql.mongorepositorydemo.repository;

import edu.whut.cs.javaee.spring.nosql.mongorepositorydemo.model.Coffee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CoffeeRepository extends MongoRepository<Coffee, String> {
    List<Coffee> findByName(String name);
}
