package com.rd.lab.pizza_service.domain.discount;

import java.math.BigDecimal;
import java.util.List;

import com.rd.lab.pizza_service.domain.Order;
import com.rd.lab.pizza_service.domain.Pizza;
import com.rd.lab.pizza_service.domain.util.CurrencyOperations;

public class OrderDiscount_MostExpensive30 extends Discount {
	private static final String title = "ORDER_DISCOUNT_MOST_EXPENSIVE_30_IF_AT_LEAST_5";
	private static final int DISCOUNT_PERCENTAGE = 30;
	private static final int MINIMAL_PIZZAS_NUMBER = 5;

	public OrderDiscount_MostExpensive30() {
		super(title);
	}

	@Override
	public BigDecimal getDiscount(Order order) {
		List<Pizza> pizzas = order.getPizzas();
		BigDecimal maxPrice = BigDecimal.ZERO;
		if (pizzas.size() >= MINIMAL_PIZZAS_NUMBER) {
			for (Pizza pizza : pizzas) {
				BigDecimal curPizzaPrice = pizza.getPrice();
				if (maxPrice.compareTo(curPizzaPrice) < 0) {
					maxPrice = curPizzaPrice;
				}
			}
			return CurrencyOperations.takePercent(maxPrice, String.valueOf(DISCOUNT_PERCENTAGE));
		}
		return maxPrice;
	}
}