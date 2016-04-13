package com.rd.lab.pizza_service.service.order_service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rd.lab.pizza_service.domain.acc_card.AccCard;
import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.domain.order.Order;
import com.rd.lab.pizza_service.domain.order.status.DefaultDoneStatus;
import com.rd.lab.pizza_service.domain.order.status.DefaultNewStatus;
import com.rd.lab.pizza_service.domain.pizza.Pizza;
import com.rd.lab.pizza_service.repository.order.InMemOrderRepository;
import com.rd.lab.pizza_service.repository.order.OrderRepository;
import com.rd.lab.pizza_service.repository.pizza.InMemPizzaRepository;
import com.rd.lab.pizza_service.repository.pizza.PizzaRepository;

@Service
public class SimpleOrderService implements OrderService {

	protected static final int DEFAULT_ORDER_MAX_PIZZAS_NUMBER = 10;

	protected int maxPizzaNumber = DEFAULT_ORDER_MAX_PIZZAS_NUMBER;
	@Autowired
	protected OrderRepository orderRep = new InMemOrderRepository();
	@Autowired
	protected PizzaRepository pizzaRep = new InMemPizzaRepository();

	@Override
	public boolean closeOrder(Long orderId) {
		Order toClose = orderRep.getOrderById(orderId);
		if (toClose != null) {
			toClose.setStatus(new DefaultDoneStatus());
			AccCard card = toClose.getCustomer().getCard();
			if (card != null) {
				card.addAmount(toClose.getCost());
			}
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
		int ordersCount = 0;
		int maxCount = pizzas.size();
		while (ordersCount < maxCount) {
			Order newOrder = new Order(customer, new DefaultNewStatus(0));
			ordersCount += newOrder.add(pizzas.subList(ordersCount, maxCount), maxPizzaNumber);
			toReturn.add(newOrder);
		}
		return toReturn;
	}

	protected List<Pizza> pizzasByIds(List<Integer> pizzasID) {
		List<Pizza> toReturn = new ArrayList<>();
		for (Integer id : pizzasID) {
			Pizza retrieved = pizzaRep.getPizzaByID(id);
			if (retrieved != null) {
				toReturn.add(retrieved);
			}
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