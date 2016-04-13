package com.rd.lab.pizza_service.domain.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.domain.order.status.DefaultNewStatus;
import com.rd.lab.pizza_service.domain.order.status.Status;
import com.rd.lab.pizza_service.domain.pizza.Pizza;
import com.rd.lab.pizza_service.domain.util.CurrencyOperations;

public class Order {
	private static Long count = 0L;
	private Long id;
	private Status status;
	private BigDecimal cost;
	private Customer customer;
	private List<Pizza> pizzas;

	public Order(Customer customer) {
		this(customer, new DefaultNewStatus());
	}

	public Order(Customer customer, Status status) {
		this.id = ++count;
		this.customer = customer;
		this.status = status;
		this.pizzas = new ArrayList<>();
	}

	public int add(List<Pizza> toAdd, int limit) {
		int toIndex = status.add(toAdd, limit);
		for (int i = 0; i < toIndex; i++) {
			pizzas.add(toAdd.get(i));
		}
		return toIndex;
	}

	public BigDecimal getCost() {
		if (this.cost == null) {
			BigDecimal cost = BigDecimal.ZERO;
			for (Pizza p : pizzas) {
				cost = CurrencyOperations.addCosts(cost, p.getPrice());
			}
			this.cost = cost;
		}
		return cost;
	}

	public Customer getCustomer() {
		return customer;
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

	public void setCost(BigDecimal cost) {
		this.cost = CurrencyOperations.setCurrencyScale(cost);
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

	public boolean setStatus(Status st) {
		if (status.setStatus(st)) {
			status = st;
			st.init();
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", status=" + status + ", cost=" + cost + ", customer="
				+ customer + ", pizzas=" + pizzas + "]";
	}

}