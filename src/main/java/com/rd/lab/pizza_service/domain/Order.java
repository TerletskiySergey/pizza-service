package com.rd.lab.pizza_service.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rd.lab.pizza_service.domain.discount.Discount;
import com.rd.lab.pizza_service.domain.util.CurrencyOperations;

public class Order {

	public enum Status {
		NEW(0), IN_PROGRESS(1), CANCELLED(2), DONE(2);

		private int priority;

		Status(int priority) {
			this.priority = priority;
		}

		public int getPriority() {
			return priority;
		}
	}

	private static Long count = 0L;

	private Long id;
	private Customer customer;
	private Status status;
	private List<Pizza> pizzas;
	private List<Discount> dsc;

	public Order(Customer customer) {
		this(customer, new ArrayList<>(), new ArrayList<>());
	}

	public Order(Customer customer, List<Pizza> pizzas) {
		this(customer, pizzas, new ArrayList<>());
	}

	public Order(Customer cust, List<Pizza> pizzas, List<Discount> dsc) {
		super();
		this.id = ++count;
		this.customer = cust;
		this.pizzas = pizzas;
		this.status = Status.NEW;
		this.dsc = dsc;
	}

	public int add(List<Pizza> toAdd, int limit) {
		int toReturn = 0;
		if (status == Status.NEW && limit > 0) {
			for (int i = 0; i < toAdd.size() && pizzas.size() <= limit; i++, toReturn++) {
				pizzas.add(toAdd.get(i));
			}
		}
		return toReturn;
	}

	public BigDecimal getFullCost() {
		BigDecimal cost = BigDecimal.ZERO;
		for (Pizza p : pizzas) {
			cost = CurrencyOperations.addCosts(cost, p.getPrice());
		}
		return cost;
	}

	public BigDecimal getFinalCost() {
		BigDecimal disc = BigDecimal.ZERO;
		for (Discount d : dsc) {
			disc = CurrencyOperations.addCosts(disc, d.getDiscount(this));
		}
		return CurrencyOperations.subtractCosts(getFullCost(), disc);
	}

	public Customer getCustomer() {
		return customer;
	}

	public List<Discount> getDiscounts() {
		return dsc;
	}

	public Long getId() {
		return id;
	}

	public List<Pizza> getPizzas() {
		return pizzas;
	}

	public Status getStatus() {
		return status;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.dsc = discounts;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

	public boolean setStatus(Status status) {
		if (this.status.getPriority() >= status.getPriority()) {
			return false;
		}
		this.status = status;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + ", pizzas=" + pizzas + ", status="
				+ status + "]";
	}

}