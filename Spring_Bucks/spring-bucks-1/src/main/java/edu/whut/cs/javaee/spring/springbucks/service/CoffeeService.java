package edu.whut.cs.javaee.spring.springbucks.service;

import edu.whut.cs.javaee.spring.springbucks.model.Coffee;
import edu.whut.cs.javaee.spring.springbucks.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "coffee")
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    public List<Coffee> getAllCoffee() {
        return coffeeRepository.findAll();
    }

    public Coffee getCoffee(Long id) {
        return coffeeRepository.findById(id).get();
    }

    public Coffee getCoffee(String name) {
        return coffeeRepository.findByName(name);
    }
}
