package com.rd.lab.pizza_service;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.domain.order.Order;
import com.rd.lab.pizza_service.service.order_service.DiscountedOrderService;

public class PizzaApp {

	public static void main(String[] args) {
		ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext(
				"annos_based_repo_config.xml");
		ConfigurableApplicationContext serviceContext = new ClassPathXmlApplicationContext(
				new String[] { "annos_based_service_config.xml" }, repoContext);
		DiscountedOrderService os = serviceContext.getBean(DiscountedOrderService.class);
		Customer cust = serviceContext.getBean(Customer.class);
		List<Integer> pizzasIds = Arrays.asList(1, 2, 3);
		List<Order> orders = os.placeNewOrders(cust, pizzasIds);
		for (Order order : orders) {
			System.out.println(order);
			System.out.println(order.getCustomer().getCard());
			os.closeOrder(order.getId());
			System.out.println(order);
			System.out.println(order.getCustomer().getCard());
		}
		repoContext.close();
		serviceContext.close();
	}
}