package com.rd.lab.pizza_service.domain.discount;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rd.lab.pizza_service.domain.order.Order;
import com.rd.lab.pizza_service.domain.order.status.DefaultNewStatus;
import com.rd.lab.pizza_service.domain.pizza.Pizza;

public class OrderDiscount_MostExpensive30Test {

	private static List<Pizza> pizzas;
	private static Order order;
	private Discount disc;

	@BeforeClass
	public static void initTestStat() {
		Pizza p1 = new Pizza("pizza1", new BigDecimal("1"), Pizza.Type.Meat);
		Pizza p2 = new Pizza("pizza2", new BigDecimal("2"), Pizza.Type.Meat);
		Pizza p3 = new Pizza("pizza3", new BigDecimal("3"), Pizza.Type.Meat);
		Pizza p4 = new Pizza("pizza4", new BigDecimal("4"), Pizza.Type.Meat);
		Pizza p5 = new Pizza("pizza5", new BigDecimal("5"), Pizza.Type.Meat);
		pizzas = Arrays.asList(p1, p2, p3, p4, p5);
		order = new Order(null, new DefaultNewStatus());
		order.setPizzas(pizzas);
	}

	@Before
	public void initTest() {
		disc = new OrderDiscount_MostExpensive30(pizzas);
	}

	@Test
	public void testGetDiscount_pizzasIsNull_shouldReturnZero() {
		disc = new OrderDiscount_MostExpensive30();
		assertEquals(0, disc.getDiscount().doubleValue(), 0);
	}

	@Test
	public void testGetDiscount_positiveCondition_shouldReturnCorrectValue() {
		assertEquals(1.5, disc.getDiscount().doubleValue(), 0);
	}

	@Test
	public void testGetDiscount_negativeCondition_shouldReturnZero() {
		disc = new OrderDiscount_MostExpensive30(pizzas.subList(0, 1));
		assertEquals(0, disc.getDiscount().doubleValue(), 0);
	}

	@Test
	public void testUpdateInstance_incopatibleType_shouldNotReact() {
		assertEquals(1.5, disc.getDiscount().doubleValue(), 0);
		disc.updateInstance(new Object());
		assertEquals(1.5, disc.getDiscount().doubleValue(), 0);
	}

	@Test
	public void testUpdateInstance_compatibleType_shouldUpdate() {
		Order emptyOrder = new Order(null, new DefaultNewStatus());
		disc.updateInstance(emptyOrder);
		assertEquals(0, disc.getDiscount().doubleValue(), 0);
		disc.updateInstance(order);
		assertEquals(1.5, disc.getDiscount().doubleValue(), 0);
	}
}
