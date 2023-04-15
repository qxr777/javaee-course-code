package edu.whut.cs.javaee.spring.orm.jpacomplexdemo.repository;

import edu.whut.cs.javaee.spring.orm.jpacomplexdemo.model.Coffee;
import org.joda.money.Money;

import java.util.List;

public interface CoffeeRepository extends BaseRepository<Coffee, Long> {
    Coffee findByName(String name);

    List<Coffee> findByPriceLessThan(Money price);
}
