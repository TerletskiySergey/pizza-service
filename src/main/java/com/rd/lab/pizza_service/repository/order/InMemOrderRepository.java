package com.rd.lab.pizza_service.repository.order;

import java.util.ArrayList;
import java.util.List;

import com.rd.lab.pizza_service.domain.Order;

public class InMemOrderRepository implements OrderRepository {

	private List<Order> orders = new ArrayList<>();

	@Override
	public Long saveOrder(Order order) {
		orders.add(order);
		return order.getId();
	}

	@Override
	public boolean updateOrder(Order order) {
		Order toUpdate = getOrderById(order.getId());
		if (toUpdate != null) {
			toUpdate.setCustomer(order.getCustomer());
			toUpdate.setDiscounts(order.getDiscounts());
			toUpdate.setPizzas(order.getPizzas());
			toUpdate.setStatus(order.getStatus());
			return true;
		}
		return false;
	}

	private Order getOrderById(Long id) {
		for (Order o : orders) {
			if (o.getId().equals(id)) {
				return o;
			}
		}
		return null;
	}

}