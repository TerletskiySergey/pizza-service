package com.rd.lab.pizza_service.repository.order;

import com.rd.lab.pizza_service.domain.order.Order;

public interface OrderRepository {
	Long saveOrder(Order order);

	Order getOrderById(Long id);

	boolean updateOrder(Order order);
}