package com.rd.lab.pizza_service.repository.pizza;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rd.lab.pizza_service.domain.pizza.Pizza;

public class InMemPizzaRepository implements PizzaRepository {

	private List<Pizza> pizzas = new ArrayList<>();
	{
		pizzas.add(new Pizza("pizza1_name", new BigDecimal("1"), Pizza.Type.Meat));
		pizzas.add(new Pizza("pizza2_name", new BigDecimal("2"), Pizza.Type.Sea));
		pizzas.add(new Pizza("pizza3_name", new BigDecimal("3"), Pizza.Type.Vegetarian));
	}

	@Override
	public Pizza getPizzaByID(Integer id) {
		for (Pizza p : pizzas) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}

}
