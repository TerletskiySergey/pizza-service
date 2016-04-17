package com.rd.lab.pizza_service.repository.pizza;

import com.rd.lab.pizza_service.domain.pizza.Pizza;

public interface PizzaRepository {

	int addPizza(Pizza toAdd);

	boolean delete(int id);

	Pizza getPizzaByID(Integer id);

	int update(Pizza toUpd);
}