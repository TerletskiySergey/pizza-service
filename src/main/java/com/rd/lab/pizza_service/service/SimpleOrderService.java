package com.rd.lab.pizza_service.service;

import java.util.ArrayList;
import java.util.List;

import com.rd.lab.pizza_service.domain.AccCard;
import com.rd.lab.pizza_service.domain.Customer;
import com.rd.lab.pizza_service.domain.Order;
import com.rd.lab.pizza_service.domain.Order.Status;
import com.rd.lab.pizza_service.domain.Pizza;
import com.rd.lab.pizza_service.domain.discount.Discount;
import com.rd.lab.pizza_service.repository.order.InMemOrderRepository;
import com.rd.lab.pizza_service.repository.order.OrderRepository;
import com.rd.lab.pizza_service.repository.pizza.InMemPizzaRepository;
import com.rd.lab.pizza_service.repository.pizza.PizzaRepository;

public class SimpleOrderService implements OrderService {

	private static final int DEFAULT_ORDER_MAX_PIZZAS_NUMBER = 10;

	private int maxPizzaNumber = DEFAULT_ORDER_MAX_PIZZAS_NUMBER;
	private OrderRepository orderRep = new InMemOrderRepository();
	private PizzaRepository pizzaRep = new InMemPizzaRepository();

	@Override
	public boolean closeOrder(Order toClose) {
		AccCard card = toClose.getCustomer().getCard();
		if (card != null) {
			card.addAmount(toClose.getFinalCost());
		}
		toClose.setStatus(Status.DONE);
		return orderRep.updateOrder(toClose);
	}

	@Override
	public List<Order> placeNewOrders(Customer customer, List<Integer> pizzasID,
			List<Discount> dsc) {
		List<Pizza> pizzas = pizzasByIds(pizzasID);
		List<Order> newOrders = createOrders(customer, pizzas, dsc);
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

	private List<Order> createOrders(Customer customer, List<Pizza> pizzas, List<Discount> dsc) {
		List<Order> toReturn = new ArrayList<>();
		int ordersCount = pizzas.size();
		while (ordersCount > 0) {
			Order curOrder = new Order(customer, pizzas, dsc);
			ordersCount -= curOrder.add(pizzas, maxPizzaNumber);
			toReturn.add(curOrder);
		}
		return toReturn;
	}

	private List<Pizza> pizzasByIds(List<Integer> pizzasID) {
		List<Pizza> toReturn = new ArrayList<>();
		for (Integer id : pizzasID) {
			toReturn.add(pizzaRep.getPizzaByID(id));
		}
		return toReturn;
	}

	private List<Order> saveOrders(List<Order> toSave) {
		for (Order order : toSave) {
			orderRep.saveOrder(order);
		}
		return toSave;
	}
}
