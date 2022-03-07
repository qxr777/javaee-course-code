package edu.whut.cs.javaee.spring.nosql.mongorepositorydemo;

import edu.whut.cs.javaee.spring.nosql.mongorepositorydemo.converter.MoneyReadConverter;
import edu.whut.cs.javaee.spring.nosql.mongorepositorydemo.model.Coffee;
import edu.whut.cs.javaee.spring.nosql.mongorepositorydemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.Date;

@Slf4j
@SpringBootApplication
@EnableMongoRepositories
public class MongoRepositoryDemoApplication implements CommandLineRunner {
	@Lazy
	@Autowired
	private CoffeeRepository coffeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(MongoRepositoryDemoApplication.class, args);
	}

	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
	}

	@Override
	public void run(String... args) throws Exception {
		Coffee espresso = Coffee.builder()
				.name("espresso")
				.price(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.createTime(new Date())
				.updateTime(new Date()).build();
		Coffee latte = Coffee.builder()
				.name("latte")
				.price(Money.of(CurrencyUnit.of("CNY"), 30.0))
				.createTime(new Date())
				.updateTime(new Date()).build();

		coffeeRepository.insert(Arrays.asList(espresso, latte));
		coffeeRepository.findAll(Sort.by("name"))
				.forEach(c -> log.info("Saved Coffee {}", c));

		Thread.sleep(1000);
		latte.setPrice(Money.of(CurrencyUnit.of("CNY"), 35.0));
		latte.setUpdateTime(new Date());
		coffeeRepository.save(latte);
		coffeeRepository.findByName("latte")
				.forEach(c -> log.info("Coffee {}", c));

		coffeeRepository.deleteAll();
	}
}

