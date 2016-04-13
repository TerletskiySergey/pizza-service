package com.rd.lab.pizza_service.domain.discount;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rd.lab.pizza_service.domain.order.Order;
import com.rd.lab.pizza_service.domain.pizza.Pizza;
import com.rd.lab.pizza_service.domain.util.CurrencyOperations;

@Component
public class OrderDiscount_MostExpensive30 implements Discount {
	private static final int DISCOUNT_PERCENTAGE = 30;
	private static final int MINIMAL_PIZZAS_NUMBER = 5;

	private List<Pizza> pizzas;

	public OrderDiscount_MostExpensive30() {
		super();
	}

	public OrderDiscount_MostExpensive30(List<Pizza> pizzas) {
		super();
		this.pizzas = pizzas;
	}

	@Override
	public BigDecimal getDiscount() {
		if (pizzas != null && pizzas.size() >= MINIMAL_PIZZAS_NUMBER) {
			BigDecimal mostExp = getBiggestPrice(pizzas);
			return CurrencyOperations.takePercent(mostExp, DISCOUNT_PERCENTAGE);
		}
		return BigDecimal.ZERO;
	}

	private BigDecimal getBiggestPrice(List<Pizza> pizzas) {
		BigDecimal maxPrice = BigDecimal.ZERO;
		if (pizzas.size() >= MINIMAL_PIZZAS_NUMBER) {
			for (Pizza pizza : pizzas) {
				BigDecimal curPizzaPrice = pizza.getPrice();
				if (maxPrice.compareTo(curPizzaPrice) < 0) {
					maxPrice = curPizzaPrice;
				}
			}
		}
		return maxPrice;
	}

	@Override
	public void updateInstance(Object input) {
		if (isApplicableFor(input)) {
			Order order = (Order) input;
			pizzas = order.getPizzas();
		}
	}

	@Override
	public boolean isApplicableFor(Object obj) {
		return obj.getClass() == Order.class;
	}
}