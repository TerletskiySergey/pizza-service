package com.rd.lab.pizza_service.repository.pizza;

import com.rd.lab.pizza_service.domain.pizza.Pizza;

public interface PizzaRepository {

	Pizza getPizzaByID(Integer id);

}