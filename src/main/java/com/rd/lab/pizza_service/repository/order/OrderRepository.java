package com.rd.lab.pizza_service.repository.order;

import com.rd.lab.pizza_service.domain.Order;

public interface OrderRepository {
	Long saveOrder(Order order);

	boolean updateOrder(Order order);
}