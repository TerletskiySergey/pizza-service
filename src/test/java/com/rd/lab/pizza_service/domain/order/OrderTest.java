package com.rd.lab.pizza_service.domain.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rd.lab.pizza_service.domain.acc_card.AccCard;
import com.rd.lab.pizza_service.domain.address.Address;
import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.domain.order.status.DefaultCancelledStatus;
import com.rd.lab.pizza_service.domain.order.status.DefaultInProgressStatus;
import com.rd.lab.pizza_service.domain.order.status.DefaultNewStatus;
import com.rd.lab.pizza_service.domain.order.status.Status;
import com.rd.lab.pizza_service.domain.pizza.Pizza;

public class OrderTest {

	private static Customer cust;
	private static List<Pizza> pizzas;

	private Order order;

	@BeforeClass
	public static void initTestStat() {
		Address addr = new Address("postalCode", "country", "city", "addrLine");
		AccCard card = new AccCard();
		cust = new Customer("name", addr, card);
		Pizza p1 = new Pizza("pizza1", new BigDecimal("1"), Pizza.Type.Meat);
		Pizza p2 = new Pizza("pizza2", new BigDecimal("2"), Pizza.Type.Meat);
		Pizza p3 = new Pizza("pizza3", new BigDecimal("3"), Pizza.Type.Meat);
		Pizza p4 = new Pizza("pizza4", new BigDecimal("4"), Pizza.Type.Meat);
		Pizza p5 = new Pizza("pizza5", new BigDecimal("5"), Pizza.Type.Meat);
		pizzas = Arrays.asList(p1, p2, p3, p4, p5);
	}

	@Before
	public void initTest() {
		order = new Order(cust, new DefaultNewStatus(0));
	}

	@Test
	public void testAdd_emptyPizzas_correctLimit_statusNew_shouldNotAadd() {
		List<Pizza> pizzas = new ArrayList<>();
		int limit = 5;
		order.add(pizzas, limit);
		assertTrue(order.getPizzas().isEmpty());
	}

	@Test
	public void testAdd_pizzasNumberWithinLimit_statusNew_shouldAddAllPizzas() {
		int limit = pizzas.size();
		assertTrue(order.getPizzas().isEmpty());
		assertEquals(pizzas.size(), order.add(pizzas, limit));
		assertEquals(pizzas.size(), order.getPizzas().size());
	}

	@Test
	public void testAdd_pizzasNumberBiggerThanLimit_statusNew_shouldNotExceedLimit() {
		int limit = pizzas.size();
		assertEquals(pizzas.size(), order.add(pizzas, limit));
		assertEquals(0, order.add(pizzas, limit));
		assertEquals(pizzas.size(), order.getPizzas().size());
	}

	@Test
	public void testAdd_zeroLimit_statusNew_shouldNotAdd() {
		int limit = pizzas.size();
		assertEquals(pizzas.size(), order.add(pizzas, limit));
		limit = 0;
		assertEquals(0, order.add(pizzas, limit));
		assertEquals(pizzas.size(), order.getPizzas().size());
	}

	@Test
	public void testAdd_pizzasNumberWithinLimit_notNewStatus_shouldNotAdd() {
		int limit = pizzas.size();
		order.setStatus(new DefaultInProgressStatus());
		assertEquals(0, order.add(pizzas, limit));
		assertEquals(0, order.getPizzas().size());
		order.setStatus(new DefaultCancelledStatus());
		assertEquals(0, order.add(pizzas, limit));
		assertEquals(0, order.getPizzas().size());
		order.setStatus(new DefaultCancelledStatus());
		assertEquals(0, order.add(pizzas, limit));
		assertEquals(0, order.getPizzas().size());
	}

	@Test
	public void testGetCost_noPizzas_shouldReturnZero() {
		assertEquals(0, order.getCost().doubleValue(), 0);
	}

	@Test
	public void testGetCost_pizzasPresent_shouldReturnCorrectCost() {
		order.setPizzas(pizzas);
		assertEquals(15, order.getCost().doubleValue(), 0);
	}

	@Test
	public void testSetStatus_newStatusHasHigherPriority_shouldChangeState() {
		Status next = new DefaultInProgressStatus();
		assertTrue(next.getPriority() > order.getStatus().getPriority());
		assertTrue(order.setStatus(next));
		assertEquals(next, order.getStatus());
	}

	@Test
	public void testSetStatus_newStatusHasLowerPriority_shouldNotChangeState() {
		order.setStatus(new DefaultCancelledStatus());
		Status curStatus = order.getStatus();
		Status nextStatus = new DefaultNewStatus(0);
		assertTrue(nextStatus.getPriority() < curStatus.getPriority());
		assertFalse(order.setStatus(nextStatus));
		assertEquals(curStatus, order.getStatus());
	}
}
