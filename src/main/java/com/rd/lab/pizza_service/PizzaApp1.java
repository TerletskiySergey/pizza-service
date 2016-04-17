package com.rd.lab.pizza_service;

import java.math.BigDecimal;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rd.lab.pizza_service.domain.pizza.Pizza;
import com.rd.lab.pizza_service.repository.pizza.PizzaRepository;

public class PizzaApp1 {
	public static void main(String[] args) {
		ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext(
				"annos_based_repo_config.xml");
		repoContext.getEnvironment().setActiveProfiles("dev");
		repoContext.refresh();
		ConfigurableApplicationContext serviceContext = new ClassPathXmlApplicationContext(
				new String[] { "annos_based_service_config.xml" }, repoContext);

		PizzaRepository pizzaRep = repoContext.getBean(PizzaRepository.class);
		// Pizza p1 = new Pizza("pizza1_name", new BigDecimal("1"),
		// Pizza.Type.Meat);
		// Pizza p2 = new Pizza("pizza2_name", new BigDecimal("2"),
		// Pizza.Type.Sea);
		// Pizza p3 = new Pizza("pizza3_name", new BigDecimal("3"),
		// Pizza.Type.Vegetarian);
		// pizzaRep.addPizza(p1);
		// pizzaRep.addPizza(p2);
		// pizzaRep.addPizza(p3);
		// System.out.println(pizzaRep.getPizzaByID(1));
		// System.out.println(pizzaRep.getPizzaByID(4));
		// System.out.println(pizzaRep.getPizzaByID(8));

		System.out.println(pizzaRep.delete(1));
		System.out.println(pizzaRep.delete(2));

		repoContext.close();
		serviceContext.close();
	}
}
