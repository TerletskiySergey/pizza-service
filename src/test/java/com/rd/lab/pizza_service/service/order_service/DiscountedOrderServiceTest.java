package com.rd.lab.pizza_service.service.order_service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rd.lab.pizza_service.domain.acc_card.AccCard;
import com.rd.lab.pizza_service.domain.address.Address;
import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.domain.discount.AccCardDiscount_10_30Max;
import com.rd.lab.pizza_service.domain.discount.Discount;
import com.rd.lab.pizza_service.domain.discount.OrderDiscount_MostExpensive30;
import com.rd.lab.pizza_service.domain.order.Order;
import com.rd.lab.pizza_service.domain.pizza.Pizza;
import com.rd.lab.pizza_service.repository.order.OrderRepository;
import com.rd.lab.pizza_service.repository.pizza.PizzaRepository;

public class DiscountedOrderServiceTest {

	private static Map<Integer, Pizza> pizzaDB;
	private static Customer cust;

	@Mock
	private OrderRepository orderRepMock;
	@Mock
	private PizzaRepository pizzaRepMock;
	@InjectMocks
	private DiscountedOrderService service;

	private Map<Long, Order> orderDB;

	@BeforeClass
	public static void initTestStat() {
		pizzaDB = new HashMap<>();
		Pizza p1 = new Pizza("pizza1", new BigDecimal("1"), Pizza.Type.Meat);
		Pizza p2 = new Pizza("pizza2", new BigDecimal("2"), Pizza.Type.Meat);
		Pizza p3 = new Pizza("pizza3", new BigDecimal("3"), Pizza.Type.Meat);
		Pizza p4 = new Pizza("pizza4", new BigDecimal("4"), Pizza.Type.Meat);
		Pizza p5 = new Pizza("pizza5", new BigDecimal("5"), Pizza.Type.Meat);
		pizzaDB.put(p1.getId(), p1);
		pizzaDB.put(p2.getId(), p2);
		pizzaDB.put(p3.getId(), p3);
		pizzaDB.put(p4.getId(), p4);
		pizzaDB.put(p5.getId(), p5);
		Address addr = new Address("postalCode", "country", "city", "addrLine");
		AccCard card = new AccCard(new BigDecimal("100"));
		cust = new Customer("name", addr, card);
	}

	@Before
	public void initTest() {
		MockitoAnnotations.initMocks(this);
		when(orderRepMock.saveOrder(isA(Order.class))).thenAnswer((o) -> {
			Order order = (Order) o.getArguments()[0];
			orderDB.put(order.getId(), order);
			return order.getId();
		});
		when(orderRepMock.updateOrder(isA(Order.class))).thenAnswer((o) -> {
			Order input = (Order) o.getArguments()[0];
			Order toUpdate = orderDB.get(input.getId());
			if (toUpdate != null && toUpdate.setStatus(input.getStatus())) {
				toUpdate.setCost(input.getCost());
				toUpdate.setCustomer(input.getCustomer());
				toUpdate.setPizzas(input.getPizzas());
				return true;
			}
			return false;
		});
		when(orderRepMock.getOrderById(anyLong())).thenAnswer((o) -> {
			Long id = (Long) o.getArguments()[0];
			return orderDB.get(id);
		});
		when(pizzaRepMock.getPizzaByID(anyInt())).thenAnswer((o) -> {
			Integer id = (Integer) o.getArguments()[0];
			return pizzaDB.get(id);
		});
		orderDB = new HashMap<>();
		service.setDsc(Arrays.asList());
	}

	@Test
	public void applyDiscountsTest_correctInput_shouldApplyCorrectly() {
		List<Order> orders = service.createOrders(cust, new ArrayList<>(pizzaDB.values()));
		service.applyDiscounts(orders);
		assertEquals(new BigDecimal("15").doubleValue(), orders.get(0).getCost().doubleValue(), 0);
		Discount dsc1 = new AccCardDiscount_10_30Max();
		Discount dsc2 = new OrderDiscount_MostExpensive30();
		service.setDsc(Arrays.asList(dsc1));
		orders = service.createOrders(cust, new ArrayList<>(pizzaDB.values()));
		service.applyDiscounts(orders);
		assertEquals(new BigDecimal("10.5").doubleValue(), orders.get(0).getCost().doubleValue(),
				0);
		service.setDsc(Arrays.asList(dsc2));
		orders = service.createOrders(cust, new ArrayList<>(pizzaDB.values()));
		service.applyDiscounts(orders);
		assertEquals(new BigDecimal("13.5").doubleValue(), orders.get(0).getCost().doubleValue(),
				0);
		service.setDsc(Arrays.asList(dsc1, dsc2));
		orders = service.createOrders(cust, new ArrayList<>(pizzaDB.values()));
		service.applyDiscounts(orders);
		assertEquals(new BigDecimal("9").doubleValue(), orders.get(0).getCost().doubleValue(), 0);
	}

}
