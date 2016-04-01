package com.rd.lab.pizza_service;

import java.util.Arrays;
import java.util.List;

import com.rd.lab.pizza_service.domain.AccCard;
import com.rd.lab.pizza_service.domain.Address;
import com.rd.lab.pizza_service.domain.Customer;
import com.rd.lab.pizza_service.domain.Order;
import com.rd.lab.pizza_service.domain.discount.AccCardDiscount_10_30Max;
import com.rd.lab.pizza_service.domain.discount.Discount;
import com.rd.lab.pizza_service.domain.discount.OrderDiscount_MostExpensive30;
import com.rd.lab.pizza_service.service.OrderService;
import com.rd.lab.pizza_service.service.SimpleOrderService;

public class PizzaApp {

	public static void main(String[] args) {
		Address addr = new Address("postalCode1", "country1", "city1", "addrLine1");
		AccCard card = new AccCard();
		Customer customer = new Customer("customer1", addr, card);
		Discount disc1 = new OrderDiscount_MostExpensive30();
		Discount disc2 = new AccCardDiscount_10_30Max(card);
		OrderService orderService = new SimpleOrderService();
		List<Integer> pizzasIds = Arrays.asList(1, 2, 1, 2, 3);
		List<Discount> discounts = Arrays.asList(disc1, disc2);
		List<Order> orders = orderService.placeNewOrders(customer, pizzasIds, discounts);
		for (Order o : orders) {
			System.out.println(o);
			System.out.println(o.getCustomer().getCard());
			orderService.closeOrder(o);
			System.out.println(o);
		}
	}
}
