package com.rd.lab.pizza_service.service.order_service;

import java.util.ArrayList;
import java.util.List;

import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.domain.order.Order;
import com.rd.lab.pizza_service.domain.order.status.DefaultDoneStatus;
import com.rd.lab.pizza_service.domain.order.status.DefaultNewStatus;
import com.rd.lab.pizza_service.domain.pizza.Pizza;
import com.rd.lab.pizza_service.repository.order.InMemOrderRepository;
import com.rd.lab.pizza_service.repository.order.OrderRepository;
import com.rd.lab.pizza_service.repository.pizza.InMemPizzaRepository;
import com.rd.lab.pizza_service.repository.pizza.PizzaRepository;

public class SimpleOrderService implements OrderService {

	protected static final int DEFAULT_ORDER_MAX_PIZZAS_NUMBER = 10;

	protected int maxPizzaNumber = DEFAULT_ORDER_MAX_PIZZAS_NUMBER;
	protected OrderRepository orderRep = new InMemOrderRepository();
	protected PizzaRepository pizzaRep = new InMemPizzaRepository();

	@Override
	public boolean closeOrder(Long orderId) {
		Order toClose = orderRep.getOrderById(orderId);
		if (toClose != null) {
			toClose.setStatus(new DefaultDoneStatus(toClose));
			return orderRep.updateOrder(toClose);
		}
		return false;
	}

	@Override
	public Order getOrderById(Long id) {
		return orderRep.getOrderById(id);
	}

	@Override
	public List<Order> placeNewOrders(Customer customer, List<Integer> pizzasID) {
		List<Pizza> pizzas = pizzasByIds(pizzasID);
		List<Order> newOrders = createOrders(customer, pizzas);
		return saveOrders(newOrders);
	}

	public void setMaxPizzaNumber(int maxPizzaNumber) {
		this.maxPizzaNumber = maxPizzaNumber;
	}

	public void setOrderRep(OrderRepository orderRep) {
		this.orderRep = orderRep;
	}

	public void setPizzaRep(PizzaRepository pizzaRep) {
		this.pizzaRep = pizzaRep;
	}

	protected List<Order> createOrders(Customer customer, List<Pizza> pizzas) {
		List<Order> toReturn = new ArrayList<>();
		int ordersCount = pizzas.size();
		Order curOrder = new Order(customer, new DefaultNewStatus(0));
		toReturn.add(curOrder);
		while (ordersCount > 0) {
			ordersCount -= curOrder.add(pizzas, maxPizzaNumber);
		}
		return toReturn;
	}

	protected List<Pizza> pizzasByIds(List<Integer> pizzasID) {
		List<Pizza> toReturn = new ArrayList<>();
		for (Integer id : pizzasID) {
			toReturn.add(pizzaRep.getPizzaByID(id));
		}
		return toReturn;
	}

	protected List<Order> saveOrders(List<Order> toSave) {
		for (Order order : toSave) {
			orderRep.saveOrder(order);
		}
		return toSave;
	}
}