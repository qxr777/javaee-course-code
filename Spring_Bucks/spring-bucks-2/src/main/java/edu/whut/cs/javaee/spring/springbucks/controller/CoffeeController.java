package edu.whut.cs.javaee.spring.springbucks.controller;

import edu.whut.cs.javaee.spring.springbucks.model.Coffee;
import edu.whut.cs.javaee.spring.springbucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coffee")
@Slf4j
public class CoffeeController {
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping(path = "/", params = "!name")
    public List<Coffee> getAll() {
        return coffeeService.findAllCoffee();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Coffee getById(@PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffee(id);
        return coffee;
    }

    @GetMapping(path = "/", params = "name")
    public Coffee getByName(@RequestParam String name) {
//        return coffeeService.getCoffee(name);
        return coffeeService.findSimpleCoffeeFromCache(name).get();
    }
}
