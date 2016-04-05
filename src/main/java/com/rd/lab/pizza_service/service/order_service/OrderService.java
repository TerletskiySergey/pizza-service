package com.rd.lab.pizza_service.service.order_service;

import java.util.List;

import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.domain.order.Order;

public interface OrderService {

	List<Order> placeNewOrders(Customer customer, List<Integer> pizzasID);

	boolean closeOrder(Long id);

	Order getOrderById(Long id);
}