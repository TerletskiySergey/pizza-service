package com.rd.lab.pizza_service.service.order_service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.domain.discount.Discount;
import com.rd.lab.pizza_service.domain.order.Order;
import com.rd.lab.pizza_service.domain.pizza.Pizza;
import com.rd.lab.pizza_service.domain.util.CurrencyOperations;

public class DiscountedOrderService extends SimpleOrderService {

	private List<Discount> dsc = new ArrayList<>();

	public DiscountedOrderService(List<Discount> dsc) {
		super();
		this.dsc = dsc;
	}

	public List<Discount> getDsc() {
		return dsc;
	}

	public void setDsc(List<Discount> dsc) {
		this.dsc = dsc;
	}

	@Override
	public List<Order> placeNewOrders(Customer customer, List<Integer> pizzasID) {
		List<Pizza> pizzas = pizzasByIds(pizzasID);
		List<Order> newOrders = createOrders(customer, pizzas);
		applyDiscounts(newOrders);
		return saveOrders(newOrders);
	}

	protected void applyDiscounts(List<Order> orders) {
		for (Order o : orders) {
			BigDecimal cost = o.getCost();
			BigDecimal disc = BigDecimal.ZERO;
			for (Discount d : dsc) {
				d.updateInstance(o);
				disc = CurrencyOperations.addCosts(disc, d.getDiscount());
			}
			cost = CurrencyOperations.subtractCosts(cost, disc);
			o.setCost(cost);
		}
	}
}