package com.rd.lab.pizza_service.service.order_service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rd.lab.pizza_service.domain.acc_card.AccCard;
import com.rd.lab.pizza_service.domain.address.Address;
import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.domain.order.Order;
import com.rd.lab.pizza_service.domain.pizza.Pizza;
import com.rd.lab.pizza_service.repository.order.OrderRepository;
import com.rd.lab.pizza_service.repository.pizza.PizzaRepository;

public class SimpleOrderServiceTest {

	private static Map<Integer, Pizza> pizzaDB = new HashMap<>();
	private static Customer cust;

	@Mock
	private OrderRepository orderRepMock;
	@Mock
	private PizzaRepository pizzaRepMock;
	@InjectMocks
	private SimpleOrderService service;

	private Map<Long, Order> orderDB;

	@BeforeClass
	public static void initTestStat() {
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
		AccCard card = new AccCard(new BigDecimal("1000"));
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
			}
			return false;
		});
		when(pizzaRepMock.getPizzaByID(anyInt())).thenAnswer((o) -> {
			Integer id = (Integer) o.getArguments()[0];
			return pizzaDB.get(id);
		});
		orderDB = new HashMap<>();
	}

	@Test
	public void testPlaceNewOrders_pizzasNumberWithinLimit_shouldCreateOneOrder() {
		service.setMaxPizzaNumber(pizzaDB.size());
		List<Order> toCheck = service.placeNewOrders(cust, Arrays.asList(1, 2, 3, 4, 5));
		assertTrue(toCheck.size() == 1);
		List<Pizza> orderPizzas = toCheck.get(0).getPizzas();
		assertEquals(5, orderPizzas.size());
		assertEquals(1, orderPizzas.get(0).getId().intValue());
		assertEquals(2, orderPizzas.get(1).getId().intValue());
		assertEquals(3, orderPizzas.get(2).getId().intValue());
		assertEquals(4, orderPizzas.get(3).getId().intValue());
		assertEquals(5, orderPizzas.get(4).getId().intValue());
	}

	@Test
	public void testPlaceNewOrders_pizzasNumberExceedsLimit_shouldCreateTwoOrders() {
		service.setMaxPizzaNumber(2);
		List<Order> toCheck = service.placeNewOrders(cust, Arrays.asList(1, 2, 3));
		assertTrue(toCheck.size() == 2);
		assertEquals(2, toCheck.get(0).getPizzas().size());
		assertEquals(1, toCheck.get(0).getPizzas().get(0).getId().intValue());
		assertEquals(2, toCheck.get(0).getPizzas().get(1).getId().intValue());
		assertEquals(1, toCheck.get(1).getPizzas().size());
		assertEquals(3, toCheck.get(1).getPizzas().get(0).getId().intValue());
	}

	@Test
	public void testPlaceNewOrders_emptyPizzaList_shouldNotCreate() {
		service.setMaxPizzaNumber(10);
		List<Order> toCheck = service.placeNewOrders(cust, Arrays.asList());
		assertTrue(toCheck.isEmpty());
		assertTrue(orderDB.isEmpty());
	}

	@Test
	public void testPlaceNewOrders_invalidIds_shouldNotCreate() {
		service.setMaxPizzaNumber(10);
		List<Order> toCheck = service.placeNewOrders(cust, Arrays.asList(7, 8, 9));
		assertTrue(toCheck.isEmpty());
		assertTrue(orderDB.isEmpty());
	}

	@Test
	public void testPlaceNewOrders_invalidAndCorrectIds_shouldCreateWithCorrectIdsOnly() {
		service.setMaxPizzaNumber(10);
		List<Order> toCheck = service.placeNewOrders(cust, Arrays.asList(1, 2, 7, 8, 9, 3));
		assertEquals(1, toCheck.size());
		assertEquals(1, orderDB.size());
		assertEquals(3, toCheck.get(0).getPizzas().size());
		assertEquals(1, toCheck.get(0).getPizzas().get(0).getId().intValue());
		assertEquals(2, toCheck.get(0).getPizzas().get(1).getId().intValue());
		assertEquals(3, toCheck.get(0).getPizzas().get(2).getId().intValue());
	}

	@Test
	public void testCloseOrder_invalidId_shouldDoNothing() {
		service.placeNewOrders(cust, Arrays.asList(1));
		assertEquals(1, orderDB.size());
		service.closeOrder(-1l);
		assertEquals(1, orderDB.size());
	}

}
