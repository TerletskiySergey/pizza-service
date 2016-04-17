package com.rd.lab.pizza_service.service.order_service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.domain.order.Order;

@ContextConfiguration(locations = {
		"classpath:/annos_based_service_test_config.xml" }, inheritInitializers = true)

public class SimpleOrderServiceTest2 extends RepositoryTestConfig {
	@Autowired
	private OrderService orderService;

	@Autowired
	private Customer cust;

	@Test
	public void testPlaceNewOrders() {
		System.out.println("placeNewOrder");
		List<Integer> pizzasIds = Arrays.asList(1);
		List<Order> result = orderService.placeNewOrders(cust, pizzasIds);
		System.out.println(result);
	}
}