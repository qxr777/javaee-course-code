package edu.whut.cs.javaee.spring.mvc.simplecontrollerdemo.controller;

import edu.whut.cs.javaee.spring.mvc.simplecontrollerdemo.model.Coffee;
import edu.whut.cs.javaee.spring.mvc.simplecontrollerdemo.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/coffee")
public class CoffeeController {
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/")
    @ResponseBody
    public List<Coffee> getAll() {
        return coffeeService.getAllCoffee();
    }
}
