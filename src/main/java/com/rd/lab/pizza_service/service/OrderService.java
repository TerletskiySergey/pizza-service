package com.rd.lab.pizza_service.service;

import java.util.List;

import com.rd.lab.pizza_service.domain.Customer;
import com.rd.lab.pizza_service.domain.Order;
import com.rd.lab.pizza_service.domain.discount.Discount;

public interface OrderService {

	List<Order> placeNewOrders(Customer customer, List<Integer> pizzasID, List<Discount> dsc);

	boolean closeOrder(Order toClose);
}