package com.rd.lab.pizza_service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.rd.lab.pizza_service.domain.acc_card.AccCard;
import com.rd.lab.pizza_service.domain.address.Address;
import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.domain.discount.AccCardDiscount_10_30Max;
import com.rd.lab.pizza_service.domain.discount.Discount;
import com.rd.lab.pizza_service.domain.discount.OrderDiscount_MostExpensive30;
import com.rd.lab.pizza_service.domain.order.Order;
import com.rd.lab.pizza_service.service.DiscountedOrderService;
import com.rd.lab.pizza_service.service.OrderService;

public class PizzaApp {

	public static void main(String[] args) {
		Address addr = new Address("postalCode1", "country1", "city1", "addrLine1");
		AccCard card = new AccCard(new BigDecimal("100"));
		Customer customer = new Customer("customer1", addr, card);
		Discount<Order> disc1 = new OrderDiscount_MostExpensive30();
		Discount<Order> disc2 = new AccCardDiscount_10_30Max();
		List<Integer> pizzasIds = Arrays.asList(1, 2, 1, 2, 3);
		List<Discount<Order>> discounts = Arrays.asList(disc1, disc2);
		OrderService orderService = new DiscountedOrderService(discounts);
		List<Order> orders = orderService.placeNewOrders(customer, pizzasIds);
		for (Order o : orders) {
			System.out.println(o);
			System.out.println(o.getCustomer().getCard());
			System.out.println(orderService.closeOrder(o.getId()));
			o = orderService.getOrderById(o.getId());
			System.out.println(o);
			System.out.println(o.getCustomer().getCard());
		}
	}
}
